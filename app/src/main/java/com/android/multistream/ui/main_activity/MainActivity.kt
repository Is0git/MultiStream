package com.android.multistream.ui.main_activity

import android.os.Bundle
import android.os.PersistableBundle
import android.transition.TransitionInflater
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.observe
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.android.multistream.R
import com.android.multistream.auth.platform_manager.PlatformManager
import com.android.multistream.auth.platforms.TwitchPlatform
import com.android.multistream.databinding.ActivityMainBinding
import com.android.multistream.network.twitch.models.v5.current_user.CurrentUser
import com.android.multistream.network.twitch.models.v5.followed_streams.StreamsItem
import com.android.multistream.ui.player.fragments.PlayerFragment
import com.android.multistream.utils.NumbersConverter
import com.android.multistream.utils.setupWithNavController
import com.bumptech.glide.Glide
import com.example.daggerviewmodelfragment.ViewModelFactory
import com.example.pagination.attach
import com.example.pagination.detach
import com.multistream.navigationdrawer.NavigationDrawer
import com.multistream.navigationdrawer.StreamsAdapter
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.android.synthetic.main.tool_bar_layout.view.menuDrawerIcon
import kotlinx.android.synthetic.main.tool_bar_layout.view.settingsIcon
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


class MainActivity : DaggerAppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    lateinit var mainActivityViewModel: MainActivityViewModel
    @Inject
    lateinit var platformManager: PlatformManager
    lateinit var streamsDrawerAdapter: StreamsAdapter<StreamsItem>
    val transition by lazy {
        TransitionInflater.from(this)
            .inflateTransition(R.transition.games_list_expand_transition)
    }
    var currentNavController: LiveData<NavController>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        mainActivityViewModel =
            ViewModelProvider(this, viewModelFactory).get(MainActivityViewModel::class.java)
        binding.motionLayout.setDefaultTransitionHandler(supportFragmentManager)
        if (savedInstanceState == null) {
            setupBottomNavigationBar()
        } // Else, need to wait for onRestoreInstanceState
        setListeners()
    }

    private fun setupBottomNavigationBar() {
        binding.apply {
            val navGraphIds = listOf(R.navigation.home, R.navigation.browse)
            currentNavController = bottomNav.setupWithNavController(
                navGraphIds,
                supportFragmentManager,
                R.id.nav_host_container,
                intent
            ) { controller: NavController, destination: NavDestination, arguments: Bundle? ->
                when (destination.id) {
                    R.id.twitchProfileFragment, R.id.mixerProfileFragment, R.id.twitchGamesViewAllFragment, R.id.twitchChannelsAllViewFragment, R.id.twitchStreamsAllViewFragment, R.id.searchFragment -> hideActionBar()
                    R.id.settingsFragment -> setVisibility(View.GONE)
                    else -> showActionBar()
                }
            }
        }
        // Whenever the selected controller changes, setup the action bar.
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        // Now that BottomNavigationBar has restored its instance state
        // and its selectedItemId, we can proceed with setting up the
        // BottomNavigationBar with Navigation
        setupBottomNavigationBar()
    }

    private fun setListeners() {
        binding.toolbarLayout.settingsIcon.setOnClickListener {
            currentNavController?.value?.navigate(
                R.id.settingsFragment
            )
        }
        binding.toolbarLayout.search_icon.setOnClickListener {
            currentNavController?.value?.navigate(
                R.id.searchFragment
            )
        }
    }

    fun hideActionBar() {
        setVisibility(View.INVISIBLE)
    }

    private fun setVisibility(visibility: Int) {
        binding.apply {
            toolbarLayout.menuDrawerIcon.visibility = visibility
            toolbarLayout.settingsIcon.visibility = visibility
            toolbarLayout.search_icon.visibility = visibility
            bottomNav.visibility = visibility
        }
    }

    fun showActionBar() {
        setVisibility(View.VISIBLE)
    }

    fun createPlayerFragment(
        channelTitle: String?,
        channelName: String?,
        channelImage: String?,
        channelCategory: String?,
        channelDisplayName: String?
    ) {
        val bundle = Bundle().apply {
            putString("channel_title", channelTitle)
            putString("channel_name", channelName)
            putString("channel_image", channelImage)
            putString("channel_category", channelCategory)
            putString("channel_display_name", channelDisplayName)
        }
        supportFragmentManager.beginTransaction()
            .setCustomAnimations(R.anim.player_fragment_animation, R.anim.player_fragment_animation)
            .replace(
                R.id.player_fragment,
                PlayerFragment::class.java,
                bundle,
                "player_fragment"
            )
            .commit()
    }

    fun initNavigationDrawer() {
        if (mainActivityViewModel.isValidated(TwitchPlatform::class.java)) {
            mainActivityViewModel.repo.pageLoader.dataLiveData.observe(this) {
                streamsDrawerAdapter.loadPaginationData(it)
            }
        }
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
            setExpandClickListener(binding.toolbarLayout.menuDrawerIcon, binding.navHostContainer)
            setStreamsListAdapter(streamsDrawerAdapter)
            recyclerView attach mainActivityViewModel.twitchFollowingChannelsPageLoader
            mainActivityViewModel.twitchFollowingChannelsPageLoader.loadInit()
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
                accounts.add(account)
                addAccounts(accounts)
            }
        }
    }

    override fun onDestroy() {
        binding.navigationDrawer.recyclerView detach mainActivityViewModel.twitchFollowingChannelsPageLoader
        super.onDestroy()
    }

    override fun onSupportNavigateUp(): Boolean {
        return currentNavController?.value?.navigateUp() ?: false
    }

}

fun View.clicks(): Flow<Unit> = callbackFlow {
    this@clicks.setOnClickListener { this.offer(Unit) }
    awaitClose { this@clicks.setOnClickListener(null) }
}
