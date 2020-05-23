package com.android.multistream.ui.main_activity

import android.os.Bundle
import android.transition.TransitionInflater
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.updateLayoutParams
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.observe
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.android.multistream.R
import com.android.multistream.auth.platform_manager.PlatformManager
import com.android.multistream.auth.platforms.TwitchPlatform
import com.android.multistream.databinding.ActivityMainBinding
import com.android.multistream.network.twitch.models.v5.current_user.CurrentUser
import com.android.multistream.network.twitch.models.v5.followed_streams.StreamsItem
import com.android.multistream.ui.player.fragments.PlayerFragment
import com.android.multistream.utils.NumbersConverter
import com.android.multistream.utils.ScreenUnit
import com.android.stripesliderview.slider.dpToPx
import com.bumptech.glide.Glide
import com.example.daggerviewmodelfragment.ViewModelFactory
import com.example.pagination.attach
import com.example.pagination.detach
import com.multistream.navigationdrawer.NavigationDrawer
import com.multistream.navigationdrawer.StreamsAdapter
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.*
import javax.inject.Inject


class MainActivity : DaggerAppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    lateinit var navController: NavController
    lateinit var mainActivityViewModel: MainActivityViewModel
    @Inject
    lateinit var platformManager: PlatformManager
    lateinit var streamsDrawerAdapter: StreamsAdapter<StreamsItem>
    val transition by lazy {
        TransitionInflater.from(this)
            .inflateTransition(R.transition.games_list_expand_transition)
    }
    var topMarginGuideline = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        mainActivityViewModel =
            ViewModelProvider(this, viewModelFactory).get(MainActivityViewModel::class.java)
        navController = findNavController(R.id.main_fragment_container)
        binding.apply {
            bottomNav.setupWithNavController(navController)
            motionLayout.setDefaultTransitionHandler(supportFragmentManager)
        }
        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            when(destination.id) {
                R.id.twitchProfileFragment, R.id.mixerProfileFragment, R.id.twitchGamesViewAllFragment, R.id.twitchChannelsAllViewFragment, R.id.twitchStreamsAllViewFragment, R.id.settingsFragment, R.id.splashScreenFragment -> hideActionBar()
                else -> showActionBar()
            }
        }
        setListeners()
        hideActionBar()
    }

    private fun setListeners() {
        binding.settingsIcon.setOnClickListener { navController.navigate(R.id.action_global_settingsFragment) }
    }

    fun hideActionBar() {
        binding.apply {
            (supportFragmentManager.findFragmentById(R.id.main_fragment_container) as NavHostFragment).requireView().updateLayoutParams<ConstraintLayout.LayoutParams> { topMargin = 0  }
            menuDrawerIcon.visibility = View.GONE
            settingsIcon.visibility = View.GONE
            bottomNav.visibility = View.GONE
        }
    }

    fun showActionBar() {
        binding.apply {
            (supportFragmentManager.findFragmentById(R.id.main_fragment_container) as NavHostFragment).requireView().updateLayoutParams<ConstraintLayout.LayoutParams> { topMargin = dpToPx(56f, resources)  }
            menuDrawerIcon.visibility = View.VISIBLE
            settingsIcon.visibility = View.VISIBLE
            bottomNav.visibility = View.VISIBLE
        }
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
            val fragmentView =
                (supportFragmentManager.findFragmentById(R.id.main_fragment_container) as NavHostFragment).requireView()
            setExpandClickListener(menuDrawerIcon, fragmentView)
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

}

fun View.clicks(): Flow<Unit> = callbackFlow {
    this@clicks.setOnClickListener { this.offer(Unit) }
    awaitClose { this@clicks.setOnClickListener(null) }
}
