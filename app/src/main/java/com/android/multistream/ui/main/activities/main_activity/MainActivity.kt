package com.android.multistream.ui.main.activities.main_activity

import android.animation.LayoutTransition
import android.animation.ObjectAnimator
import android.os.Bundle
import android.transition.TransitionInflater
import android.transition.TransitionManager
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.android.multistream.R
import com.android.multistream.databinding.ActivityMainBinding
import com.android.multistream.ui.player.fragments.PlayerFragment
import com.android.multistream.utils.ViewModelFactory
import com.bumptech.glide.Glide
import com.multistream.navigationdrawer.NavigationDrawer
import com.multistream.navigationdrawer.StreamsAdapter
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : DaggerAppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    lateinit var navController: NavController

    lateinit var mainActivityViewModel: MainActivityViewModel
    val transition by lazy {
        TransitionInflater.from(this)
            .inflateTransition(R.transition.games_list_expand_transition)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        mainActivityViewModel =
            ViewModelProvider(this, viewModelFactory).get(MainActivityViewModel::class.java)

        navController = findNavController(R.id.main_fragment_container)

        binding.apply {
            bottomNav.setupWithNavController(navController)
            motionLayout.setDefaultTransitionHandler(supportFragmentManager)
            navigationDrawer.setExpandClickListener(menuDrawerIcon)
        }

        setListeners()

        hideActionBar()

        val listOfItems = mutableListOf(
            AdapterItem(
                "https://static-cdn.jtvnw.net/jtv_user_pictures/1eb304dc-27da-411a-9fc2-d68addbbab14-profile_image-70x70.png",
                "Greekgodx",
                "COD: MODERN WRAFARE",
                "12454"
            ),
            AdapterItem(
                "https://static-cdn.jtvnw.net/jtv_user_pictures/d0d8b385-823b-4de5-8ec5-3737bc1c233c-profile_image-70x70.png",
                "Myth",
                "VALORANT",
                "10001"
            ),
            AdapterItem(
                "https://static-cdn.jtvnw.net/jtv_user_pictures/1f47965f-7961-4b64-ad6f-71808d7d7fe9-profile_image-70x70.png",
                "TrainswrecksTV",
                "COUNTER-STRIKE: GO",
                "6898"
            ),
            AdapterItem(
                "https://static-cdn.jtvnw.net/jtv_user_pictures/c901b680-9876-4e67-826a-e141381628e5-profile_image-70x70.jpg",
                "M0xy",
                "VALORANT",
                "2134"
            ),
            AdapterItem(
                "https://static-cdn.jtvnw.net/jtv_user_pictures/774f1524-f873-4e60-b767-b17653a74ab5-profile_image-70x70.png",
                "AndyPyro",
                "COUNTER-STRIKE: GO",
                "1898"
            )
        )

        val adapter = StreamsAdapter<AdapterItem> { item, holder ->
            holder.binding.apply {
                Glide.with(this@MainActivity).load(item.imageUrl).centerCrop().into(streamerImage)
                gameName.text = item.gameName
                username.text = item.streamName
                viewerCount.text = item.viewersCount
            }
        }



        binding.navigationDrawer.apply {
            setStreamsListAdapter(adapter)
            tabLayout.addTab(tabLayout.newTab().apply {
                title = "MIXER"
                text = "MIXER"
            })
            tabLayout.addTab(tabLayout.newTab().apply {
                title = "TWITCXH"
                text = "TWITCH"
            })
            Glide.with(this@MainActivity)
                .load("https://static-cdn.jtvnw.net/jtv_user_pictures/1eb304dc-27da-411a-9fc2-d68addbbab14-profile_image-70x70.png")
                .centerCrop().into(userImage)

            setUsername("is0xxx")
        }

        adapter.dataItems = listOfItems
        adapter.notifyDataSetChanged()
    }


    data class AdapterItem(
        var imageUrl: String,
        var streamName: String,
        var gameName: String,
        var viewersCount: String
    )

    private fun setListeners() {
        binding.settingsIcon.setOnClickListener { navController.navigate(R.id.action_global_settingsFragment) }
    }

    fun hideActionBar() {
        binding.apply {
            menuDrawerIcon.visibility = View.GONE
            settingsIcon.visibility = View.GONE
            bottomNav.visibility = View.GONE
        }
    }

    fun showActionBar() {
        binding.apply {
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
            .replace(R.id.player_fragment, PlayerFragment::class.java, bundle, "player_fragment")
            .commit()
    }


}
