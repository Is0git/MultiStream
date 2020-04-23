package com.android.multistream.ui.main.fragments.home_fragment

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.viewpager2.widget.ViewPager2
import com.android.multistream.R
import com.android.multistream.databinding.*
import com.android.multistream.di.qualifiers.AuthPreferencesQualifier
import com.android.multistream.di.qualifiers.SettingsPreferencesQualifier
import com.android.multistream.network.twitch.models.new_twitch_api.top_games.Data
import com.android.multistream.network.twitch.models.new_twitch_api.channels.DataItem
import com.android.multistream.network.twitch.models.v5.followed_streams.StreamsItem
import com.android.multistream.ui.main.activities.main_activity.MainActivity
import com.android.multistream.ui.main.activities.main_activity.MainActivityViewModel
import com.android.multistream.ui.main.fragments.home_fragment.decorations.HorizontalMarginItemDecoration
import com.android.multistream.ui.main.fragments.home_fragment.view_model.HomeFragmentViewModel
import com.android.multistream.ui.widgets.hide_scroll_view.HideScrollView.Companion.RIGHT
import com.android.multistream.ui.widgets.hide_scroll_view.animations.AlphaAnimation
import com.android.multistream.utils.PlaceHolderAdapter
import com.android.multistream.utils.ViewModelFactory
import com.android.multistream.utils.data_binding.ImageLoader
import com.ramotion.cardslider.CardSliderLayoutManager
import dagger.android.support.DaggerFragment
import java.util.*
import javax.inject.Inject

class HomeFragment : DaggerFragment() {
    lateinit var binding: HomeFragmentBinding
    @Inject
    lateinit var factory: ViewModelFactory

    lateinit var settingsPreferences: SharedPreferences

    lateinit var channelsViewPagerAdapter: PlaceHolderAdapter<DataItem, ChannelsViewPagerItemBinding>
    lateinit var twitchChannelsAdapter: PlaceHolderAdapter<StreamsItem, ListItemTwoBinding>
    lateinit var followingAdapter: PlaceHolderAdapter<StreamsItem, ListItemThreeBinding>
    lateinit var topGamesAdapter: PlaceHolderAdapter<Data, ListItemFourBinding>
    lateinit var mainActivityViewModel: MainActivityViewModel
    lateinit var viewModel: HomeFragmentViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = HomeFragmentBinding.inflate(inflater, container, false)

        viewModel = ViewModelProvider(this, factory).get(HomeFragmentViewModel::class.java)
        mainActivityViewModel =
            ViewModelProvider(requireActivity()).get(MainActivityViewModel::class.java)

        setupViewPager()
        val value = settingsPreferences.getBoolean(getString(R.string.mixer_visibility), true)
        value
        if (settingsPreferences.getBoolean(getString(R.string.twitch_visibility), false)) setupTwitchUI() else hideTwitch()

        if (settingsPreferences.getBoolean(getString(R.string.mixer_visibility), false)) setupMixerUI() else hideMixer()

        binding.hideScrollView.apply {
            val alphaAnimation =
                AlphaAnimation(this, animationHandler.topDivider, animationHandler.bottomDivider)

            addHiddenView(binding.homeText, RIGHT, alphaAnimation)
        }
        (requireActivity() as MainActivity).showActionBar()
        return binding.root
    }

    private fun setupViewPager() {
        channelsViewPagerAdapter =
            PlaceHolderAdapter(R.layout.channels_view_pager_item) { binding, item ->
                binding.viewPagerCard.cancelAnimation()
                binding.data = item
            }

        val nextItemVisiblePx = resources.getDimension(R.dimen.viewpager_next_item_visible)
        val currentItemHorizontalMarginPx =
            resources.getDimension(R.dimen.viewpager_current_item_horizontal_margin)
        val pageTranslationX = nextItemVisiblePx + currentItemHorizontalMarginPx
        val pageTransformer = ViewPager2.PageTransformer { page: View, position: Float ->
            page.translationX = -pageTranslationX * position
            page.scaleY = 1 - (0.25f * kotlin.math.abs(position))

        }

        binding.channelsViewPager.apply {

            adapter = channelsViewPagerAdapter
            offscreenPageLimit = 1
            setPageTransformer(pageTransformer)

        }
        val itemDecoration = HorizontalMarginItemDecoration(
            requireContext(),
            R.dimen.viewpager_current_item_horizontal_margin
        )
        binding.channelsViewPager.addItemDecoration(itemDecoration)
    }

    //TWITCH

    private fun setupTwitchUI() {
        setupTwitchLists()
        observeTwitch()
    }

    private fun setupTwitchLists() {
        twitchChannelsAdapter = PlaceHolderAdapter(R.layout.list_item_two, false) { k, t ->
            k.apply {
                viewersCount.text = t.viewers.toString()
                ImageLoader.loadImageTwitch(streamImage, t.preview?.large)
                t.channel.also { channel ->
                    streamTitle.text = channel?.status
                    ImageLoader.loadImageTwitch(streamerBanner, channel?.logo)
                    streamGame.text = channel?.game
                    streamerName.text = channel?.name
                }
            }
        }


        followingAdapter = PlaceHolderAdapter(R.layout.list_item_three, false) { k, t ->
            k.apply {
                t.channel.also { channel ->
                    ImageLoader.loadImageTwitch(streamerBanner, channel?.logo)
                    streamerName.text = channel?.name?.toUpperCase(Locale.getDefault())
                    ImageLoader.loadImage(streamerProfileBanner, t.channel?.logo)
                }
            }
        }

        topGamesAdapter = PlaceHolderAdapter(
            R.layout.list_item_four,
            false
        ) { k, t ->
            ImageLoader.loadImageTwitch(k.gameBanner, t.box_art_url)
        }


        binding.apply {
            followingStreamsList.adapter = followingAdapter
            liveFollowingStreamsList.adapter = twitchChannelsAdapter
            twitchTopGamesList.also {
                it.layoutManager = CardSliderLayoutManager(requireActivity())
                it.adapter = topGamesAdapter
            }
        }
    }

    private fun observeTwitch() {
        viewModel.apply {
            topChannelsLiveData.observe(viewLifecycleOwner) {
                channelsViewPagerAdapter.data = it
            }

            followedLiveStreamsLiveData.observe(viewLifecycleOwner) {
                twitchChannelsAdapter.data = it.streams

            }

            followedStreamsLiveData.observe(viewLifecycleOwner) {
                followingAdapter.data = it.streams
            }

            topGamesLiveData.observe(viewLifecycleOwner) {
                topGamesAdapter.data = it
                binding.twitchTopGamesList.scrollToPosition(4)
            }
        }
    }


    private fun hideTwitch() {
        binding.apply {
            twitchFollowingChannelsText.visibility = View.GONE
            twitchFollowingLiveStreamsText.visibility = View.GONE
            twitchTopGamesText.visibility = View.GONE
            twitchText.visibility = View.GONE
            twitchLogo.visibility = View.GONE
        }
    }

    //Mixer

    private fun setupMixerUI() {

    }

    private fun hideMixer() {
        binding.apply {
            mixerText.visibility = View.GONE
            mixerRecommendedChannels.visibility = View.GONE
            mixerTopChannelsText.visibility = View.GONE
        }
    }
}