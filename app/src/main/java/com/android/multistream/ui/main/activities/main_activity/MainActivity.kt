package com.android.multistream.ui.main.activities.main_activity

import android.R.attr.radius
import android.os.Bundle
import android.transition.TransitionInflater
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.ViewCompat
import androidx.core.view.marginTop
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.findFragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.android.multistream.R
import com.android.multistream.auth.PlatformManager
import com.android.multistream.auth.platforms.TwitchPlatform
import com.android.multistream.databinding.ActivityMainBinding
import com.android.multistream.network.twitch.models.v5.user.CurrentUser
import com.android.multistream.ui.main.activities.main_activity.MainActivity
import com.android.multistream.ui.player.fragments.PlayerFragment
import com.android.multistream.utils.ScreenUnit
import com.android.multistream.utils.ViewModelFactory
import com.bumptech.glide.Glide
import com.google.android.material.shape.CornerFamily
import com.google.android.material.shape.MaterialShapeDrawable
import com.google.android.material.shape.ShapeAppearanceModel
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

    @Inject
    lateinit var platformManager: PlatformManager

    val transition by lazy {
        TransitionInflater.from(this)
            .inflateTransition(R.transition.games_list_expand_transition)
    }

    var topMarginGuideline = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        mainActivityViewModel =
            ViewModelProvider(this, viewModelFactory).get(MainActivityViewModel::class.java)
        navController = findNavController(R.id.main_fragment_container)
        binding.apply {
            bottomNav.setupWithNavController(navController)
//           val radius =  ScreenUnit.convertDpToPixel(25f).toFloat()
//            val shapeAppearanceModel = ShapeAppearanceModel()
//                .toBuilder()
//                .setTopRightCorner(CornerFamily.ROUNDED, radius)
//                .setTopLeftCorner(CornerFamily.ROUNDED, radius)
//                .build()
//
//            ViewCompat.setBackground(bottomNav, MaterialShapeDrawable(shapeAppearanceModel))
            motionLayout.setDefaultTransitionHandler(supportFragmentManager)
        }
        setListeners()
        hideActionBar()
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
        topMarginGuideline = binding.toolBarGuideline.marginTop
        binding.apply {
            binding.toolBarGuideline.layoutParams = (binding.toolBarGuideline.layoutParams as ConstraintLayout.LayoutParams).also { it.topMargin = 0 }
            menuDrawerIcon.visibility = View.GONE
            settingsIcon.visibility = View.GONE
            bottomNav.visibility = View.GONE
        }
    }

    fun showActionBar() {
        binding.apply {
            binding.toolBarGuideline.layoutParams = (binding.toolBarGuideline.layoutParams as ConstraintLayout.LayoutParams).also { it.topMargin = topMarginGuideline }
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

    fun initNavigationDrawer() {

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
            ),
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
            ), AdapterItem(
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
        adapter.dataItems = listOfItems
        binding.navigationDrawer.apply {
            val fragmentView =
                (supportFragmentManager.findFragmentById(R.id.main_fragment_container) as NavHostFragment).requireView()
            setExpandClickListener(menuDrawerIcon, fragmentView)
            setStreamsListAdapter(adapter)
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
}
