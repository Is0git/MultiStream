package com.android.multistream.ui.main.fragments.home_fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.observe
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.android.multistream.R
import com.android.multistream.databinding.*
import com.android.multistream.network.twitch.models.new_twitch_api.top_games.Data
import com.android.multistream.network.twitch.models.new_twitch_api.channels.DataItem
import com.android.multistream.network.twitch.models.v5.followed_streams.StreamsItem
import com.android.multistream.ui.main.activities.main_activity.MainActivity
import com.android.multistream.ui.main.fragments.home_fragment.decorations.HorizontalMarginItemDecoration
import com.android.multistream.ui.main.fragments.home_fragment.view_model.HomeFragmentViewModel
import com.android.multistream.ui.widgets.hide_scroll_view.HideScrollView.Companion.LEFT
import com.android.multistream.ui.widgets.hide_scroll_view.HideScrollView.Companion.RIGHT
import com.android.multistream.ui.widgets.hide_scroll_view.animations.AlphaAnimation
import com.android.multistream.ui.widgets.hide_scroll_view.animations.TranslationXAnimation
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
    lateinit var channelsViewPagerAdapter: PlaceHolderAdapter<DataItem, ChannelsViewPagerItemBinding>
    lateinit var twitchChannelsAdapter: PlaceHolderAdapter<StreamsItem, ListItemTwoBinding>
    lateinit var followingAdapter: PlaceHolderAdapter<StreamsItem, ListItemThreeBinding>
    lateinit var topGamesAdapter: PlaceHolderAdapter<Data, ListItemFourBinding>

    lateinit var viewModel: HomeFragmentViewModel
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = HomeFragmentBinding.inflate(inflater, container, false)
        viewModel = ViewModelProviders.of(this, factory).get(HomeFragmentViewModel::class.java)
        setupViewPager()
        setupLists()
        observe()
        binding.hideScrollView.translationX


        binding.hideScrollView.apply {
            val translationXAnimation =
                TranslationXAnimation(
                    this,
                    animationHandler.topDivider, animationHandler.bottomDivider
                )
            val alphaAnimation = AlphaAnimation(this, animationHandler.topDivider, animationHandler.bottomDivider)

            addHiddenView(binding.homeText, RIGHT, alphaAnimation)
        }

        (activity as MainActivity).showActionBar()
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
            context!!,
            R.dimen.viewpager_current_item_horizontal_margin
        )
        binding.channelsViewPager.addItemDecoration(itemDecoration)
    }


    private fun setupLists() {
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


        followingAdapter =  PlaceHolderAdapter(R.layout.list_item_three, false) { k, t ->
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
                it.layoutManager = CardSliderLayoutManager(activity!!)
                it.adapter = topGamesAdapter
            }
        }
    }

    private fun observe() {
        viewModel.apply {
            topChannelsLiveData.observe(viewLifecycleOwner) {
                channelsViewPagerAdapter.data = it
            }

            followedLiveStreamsLiveData.observe(viewLifecycleOwner) {
                twitchChannelsAdapter.data = it.streams

            }

//            followedStreamsLiveData.observe(viewLifecycleOwner) {
//                followingAdapter.data = it.streams
//            }

            topGamesLiveData.observe(viewLifecycleOwner) {
                topGamesAdapter.data = it
                binding.twitchTopGamesList.scrollToPosition(4)
            }
        }
    }
}