package com.android.multistream.ui.main_activity

import android.annotation.SuppressLint
import android.content.SharedPreferences
import android.graphics.Rect
import android.os.Bundle
import android.transition.Transition
import android.transition.TransitionInflater
import android.util.Log
import android.view.MotionEvent
import android.view.TouchDelegate
import android.view.View
import android.view.WindowManager
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.fragment.NavHostFragment
import com.android.multistream.R
import com.android.multistream.auth.platform_manager.PlatformManager
import com.android.multistream.auth.platforms.TwitchPlatform
import com.android.multistream.databinding.ActivityMainBinding
import com.android.multistream.di.qualifiers.SettingsPreferencesQualifier
import com.android.multistream.network.twitch.models.v5.current_user.CurrentUser
import com.android.multistream.network.twitch.models.v5.followed_streams.StreamsItem
import com.android.multistream.ui.main_activity.fragments.settings_fragment.SettingsFragment
import com.android.multistream.ui.main_activity.fragments.settings_fragment.SettingsLoader
import com.android.multistream.ui.player.fragments.PlayerFragment
import com.android.multistream.ui.player.fragments.live_stream_player_fragment.LiveStreamPlayerFragment
import com.android.multistream.ui.player.fragments.vod_player_fragment.VodPlayerFragment
import com.android.multistream.utils.NumbersConverter
import com.android.multistream.utils.setupWithNavController
import com.bumptech.glide.Glide
import com.example.daggerviewmodelfragment.ViewModelFactory
import com.example.pagination.attach
import com.example.pagination.detach
import com.multistream.navigationdrawer.NavigationDrawer
import com.multistream.navigationdrawer.StreamsAdapter
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject
import kotlin.math.absoluteValue


class MainActivity : DaggerAppCompatActivity(), View.OnTouchListener,
    MotionLayout.TransitionListener {

    lateinit var binding: ActivityMainBinding
    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    lateinit var mainActivityViewModel: MainActivityViewModel
    @Inject
    lateinit var platformManager: PlatformManager
    lateinit var streamsDrawerAdapter: StreamsAdapter<StreamsItem>
    val transition: Transition by lazy {
        TransitionInflater.from(this)
            .inflateTransition(R.transition.games_list_expand_transition)
    }
    private var currentNavController: LiveData<NavController>? = null
    @Inject
    lateinit var settingsLoader: SettingsLoader
    @Inject
    @SettingsPreferencesQualifier
    lateinit var preferences: SharedPreferences
    private var scrollDistance = 0f
    private var lastX = 0f
    private var currentTransition = R.id.start

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE or WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        mainActivityViewModel =
            ViewModelProvider(this, viewModelFactory).get(MainActivityViewModel::class.java)
        if (savedInstanceState == null) setupBottomNavigationBar()
        setListeners()
        configureMotionLayout()
        if (savedInstanceState != null) {
            mainActivityViewModel.twitchFollowingChannelsPageLoader.invalidate(false)
        }
        initNavigationDrawer()
        binding.navigationDrawer.forceLayout()
        binding.motionLayout.post { increaseNavTouchRegion() }
    }

    private fun setupBottomNavigationBar() {
        binding.apply {
            val navGraphIds = listOf(R.navigation.home, R.navigation.library, R.navigation.browse)
            currentNavController = bottomNav.setupWithNavController(
                navGraphIds,
                supportFragmentManager,
                R.id.nav_host_container,
                intent
            ) { controller: NavController, destination: NavDestination, arguments: Bundle? ->
                when (destination.id) {
                    R.id.twitchProfileFragment, R.id.mixerProfileFragment, R.id.twitchGamesViewAllFragment, R.id.twitchChannelsAllViewFragment, R.id.twitchStreamsAllViewFragment, R.id.searchFragment, R.id.mixerChannelsAllViewFragment, R.id.mixerTopGamesViewAllFragment -> hideActionBar()
                    else -> showActionBar()
                }
            }
        }
    }


    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        // Now that BottomNavigationBar has restored its instance state
        // and its selectedItemId, we can proceed with setting up the
        // BottomNavigationBar with Navigation
        val transitionState = savedInstanceState?.getInt("activity_motion_state")
        if (transitionState != null) binding.motionLayout.transitionToState(transitionState)
        setupBottomNavigationBar()
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setListeners() {
        binding.apply {
            motionLayout.setTransitionListener(this@MainActivity)
            navigationDrawer.setOnTouchListener(this@MainActivity)
        }
        binding.settingsIcon.setOnClickListener {
            supportFragmentManager.apply {
                var settingsFragment = findFragmentByTag("settings_fragment")
                if (settingsFragment != null) beginTransaction().remove(settingsFragment).commitNow()
                settingsFragment = SettingsFragment()
                beginTransaction()
                    .replace(R.id.settings_fragment_container, settingsFragment, "settings_fragment")
                    .addToBackStack(null)
                    .setPrimaryNavigationFragment(settingsFragment)
                    .commitAllowingStateLoss()
            }
        }
        binding.searchIcon.setOnClickListener {
            onSearchClick()
        }
    }

    private fun getSearchFragment(): Fragment {
        return supportFragmentManager.findFragmentByTag("search_fragment")
            ?: NavHostFragment.create(R.navigation.search_nav)
    }

    private fun onSearchClick() {
        val fragment = getSearchFragment() as NavHostFragment
        supportFragmentManager.beginTransaction()
            .add(R.id.search_fragment_container, fragment, "search_fragment")
            .addToBackStack(null)
            .setPrimaryNavigationFragment(fragment)
            .commit()
    }

    private fun hideActionBar() {
        setVisibility(View.INVISIBLE)
    }

    private fun setVisibility(visibility: Int) {
        binding.apply {
            menuDrawerIcon.visibility = visibility
            settingsIcon.visibility = visibility
            searchIcon.visibility = visibility
            bottomNav.visibility = visibility
        }
    }

    private fun showActionBar() {
        setVisibility(View.VISIBLE)
    }

    fun initLiveStreamPlayerFragment(
        channelTitle: String?,
        channelName: String?,
        channelImage: String?,
        channelCategory: String?,
        channelDisplayName: String?,
        channelId: String,
        viewerCount: Int?

    ) {
        val bundle = addCommonPlayerFragmentArgs(
            channelTitle,
            channelName,
            channelImage,
            channelCategory,
            channelDisplayName,
            channelId
        ).also { it.putInt("viewer_count", viewerCount ?: 0) }
        createFragmentAndStartPlayer(bundle, LiveStreamPlayerFragment::class.java)
    }

    fun initVodPlayerFragment(
        channelTitle: String?,
        channelName: String?,
        channelImage: String?,
        channelCategory: String?,
        channelDisplayName: String?,
        channelId: String,
        viewerCount: Int?,
        vodId: String
    ) {
        val bundle = addCommonPlayerFragmentArgs(
            channelTitle,
            channelName,
            channelImage,
            channelCategory,
            channelDisplayName,
            channelId
        ).also { it.putString("vodId", vodId)
        it.putInt("view_count", viewerCount ?: 0)}
        createFragmentAndStartPlayer(bundle, VodPlayerFragment::class.java)
    }

    private fun addCommonPlayerFragmentArgs(
        channelTitle: String?,
        channelName: String?,
        channelImage: String?,
        channelCategory: String?,
        channelDisplayName: String?,
        channelId: String
    ): Bundle {
        return Bundle().apply {
            putString("channel_title", channelTitle)
            putString("channel_name", channelName)
            putString("channel_image", channelImage)
            putString("channel_category", channelCategory)
            putString("channel_display_name", channelDisplayName)
            putString("channel_id", channelId)
        }
    }

    private fun createFragmentAndStartPlayer(
        arguments: Bundle,
        fragmentClass: Class<out PlayerFragment<*>>
    ) {
        supportFragmentManager.apply {
            val searchFragment = findFragmentByTag("search_fragment")
            if(searchFragment != null && searchFragment.isAdded) beginTransaction().remove(searchFragment).commitNow()
            val lastFragment = findFragmentByTag("player_fragment")
            lastFragment?.also { beginTransaction().remove(it).commitNow() }
            val fragment = fragmentClass.newInstance()
            fragment.arguments = arguments
            beginTransaction()
                .setCustomAnimations(R.anim.player_fragment_animation, R.anim.player_fragment_animation)
                .replace(R.id.player_fragment, fragment, "player_fragment")
                .runOnCommit {binding.motionLayout.transitionToEnd()  }
                .commitNow()
        }
    }

    private fun initNavigationDrawer() {
        if (preferences.getBoolean(
                getString(R.string.twitch_sync),
                true
            ) && preferences.getBoolean(
                getString(R.string.twitch_visibility),
                true
            )
        ) {
            binding.navigationDrawer.recyclerView attach mainActivityViewModel.twitchFollowingChannelsPageLoader
            mainActivityViewModel.repo.pageLoader.dataLiveData.observe(this) {
                streamsDrawerAdapter.loadPaginationData(it)
                binding.navigationDrawer.recyclerView.apply {
//                    postDelayed({scrollToPosition(0)}, 500)
                }
            }
            mainActivityViewModel.twitchFollowingChannelsPageLoader.loadInit()
            streamsDrawerAdapter = StreamsAdapter { item, holder ->
                holder.binding.apply {
                    Glide.with(this@MainActivity).load(item.channel?.logo).centerCrop()
                        .into(streamerImage)
                    gameName.text = item.game
                    username.text = item.channel?.display_name
                    viewerCount.text = NumbersConverter.getK(item.viewers, this@MainActivity)
                }
            }
            binding.navigationDrawer.apply {
                setStreamsListAdapter(streamsDrawerAdapter)
                val accounts = mutableListOf<NavigationDrawer.Account>()
                platformManager.platforms.forEach {
                    if (!it.value.isValidated) return
                    var name: String? = null
                    var imageUrl: String? = null
                    when (it.value) {
                        is TwitchPlatform -> (it.value.currentUser as CurrentUser?).apply {
                            name = this?.name
                            imageUrl = this?.logo
                        }
                    }
                    val account = NavigationDrawer.Account(
                        name,
                        imageUrl ?: "null",
                        it.value.platformName ?: "null"
                    )
                    onRecyclerViewItemClick =
                        object : NavigationDrawer.OnRecyclerViewItemClick<Any?> {
                            override fun onClick(item: Any?, position: Int) {
                                if (binding.motionLayout.getTransition(R.id.drawer_transition) != null) binding.motionLayout.transitionToStart()
                                postDelayed(
                                    {
                                        (item as StreamsItem).also { streamItem ->
                                            initLiveStreamPlayerFragment(
                                                streamItem.channel?.status,
                                                streamItem.channel?.name,
                                                streamItem.channel?.logo,
                                                streamItem.game,
                                                streamItem.channel?.display_name,
                                                streamItem.channel?._id.toString(),
                                                streamItem.viewers
                                            )
                                        }
                                    },
                                    resources.getInteger(android.R.integer.config_mediumAnimTime).toLong()
                                )
                            }
                        }
                    accounts.add(account)
                    addAccounts(accounts)
                }
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putInt("activity_motion_state", currentTransition)
        super.onSaveInstanceState(outState)
    }

    override fun onDestroy() {
        binding.navigationDrawer.recyclerView detach mainActivityViewModel.twitchFollowingChannelsPageLoader
        super.onDestroy()
    }

    private fun configureMotionLayout() {

    }

    private fun increaseNavTouchRegion() {
        binding.navigationDrawer.isEnabled = true
        val delegateArea = Rect()
        binding.navigationDrawer.getHitRect(delegateArea)
        delegateArea.right += delegateArea.left.absoluteValue + 500
        binding.navigationDrawer.touchRect
        (binding.navigationDrawer.parent as View).touchDelegate =
            TouchDelegate(delegateArea, binding.navigationDrawer)
        binding.navigationDrawer.touchRect
    }

    override fun onSupportNavigateUp(): Boolean {
        return currentNavController?.value?.navigateUp() ?: false
    }


    @SuppressLint("ClickableViewAccessibility")
    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        binding.apply {
            when (event?.action) {
                MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                    when (currentTransition) {
                        R.id.start -> {
                            scrollDistance = if (motionLayout.progress >= 0.25f) {
                                motionLayout.transitionToEnd()
                                binding.navigationDrawer.right.toFloat()
                            } else {
                                motionLayout.transitionToStart()
                                0f
                            }
                        }
                        R.id.show_drawer -> {
                            scrollDistance = if (motionLayout.progress <= 0.75f) {
                                motionLayout.transitionToStart()
                                0f
                            } else {
                                motionLayout.transitionToEnd()
                                navigationDrawer.right.toFloat()
                            }
                        }
                    }
                    return false
                }
                MotionEvent.ACTION_MOVE -> {
                    val scrolledX = lastX - event.x
                    scrollDistance = if (scrolledX < 0) {
                        (scrollDistance + scrolledX.absoluteValue).coerceAtMost(navigationDrawer.right.toFloat())
                    } else {
                        (scrollDistance - scrolledX).coerceAtLeast(navigationDrawer.left.toFloat())
                    }
                    val position = scrollDistance.absoluteValue / navigationDrawer.width
                    motionLayout.progress = position
                    return true
                }
                MotionEvent.ACTION_DOWN -> {
                    motionLayout.setTransition(R.id.drawer_transition)
                    scrollDistance =
                        if (currentTransition == R.id.show_drawer) navigationDrawer.right.toFloat() else 0f
                    lastX = event.x
                    Log.d("DOWN", "DSSd")
                    return true
                }
            }
            return false
        }
    }

    override fun onTransitionTrigger(p0: MotionLayout?, p1: Int, p2: Boolean, p3: Float) {}
    override fun onTransitionStarted(p0: MotionLayout?, p1: Int, p2: Int) {}
    override fun onTransitionChange(p0: MotionLayout?, p1: Int, p2: Int, p3: Float) {}

    override fun onTransitionCompleted(p0: MotionLayout?, p1: Int) {
        currentTransition = p1
    }
}

fun View.clicks(): Flow<Unit> = callbackFlow {
    this@clicks.setOnClickListener { this.offer(Unit) }
    awaitClose { this@clicks.setOnClickListener(null) }
}
