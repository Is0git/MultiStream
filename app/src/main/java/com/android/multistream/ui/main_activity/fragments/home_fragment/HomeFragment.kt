package com.android.multistream.ui.main_activity.fragments.home_fragment

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.android.multistream.R
import com.android.multistream.databinding.*
import com.android.multistream.di.qualifiers.SettingsPreferencesQualifier
import com.android.multistream.network.mixer.models.mixer_channels.MixerGameChannel
import com.android.multistream.network.mixer.models.top_games.MixerTopGames
import com.android.multistream.network.twitch.models.new_twitch_api.channels.DataItem
import com.android.multistream.network.twitch.models.v5.followed_streams.StreamsItem
import com.android.multistream.network.twitch.models.v5.top_games.TopItem
import com.android.multistream.ui.main_activity.MainActivity
import com.android.multistream.ui.main_activity.fragments.home_fragment.decorations.HorizontalMarginItemDecoration
import com.android.multistream.ui.main_activity.fragments.home_fragment.view_model.HomeFragmentViewModel
import com.android.multistream.utils.NumbersConverter
import com.android.multistream.utils.PlaceHolderAdapter
import com.android.multistream.utils.data_binding.ImageLoader
import com.android.multistream.utils.getMixerImageUrl
import com.example.daggerviewmodelfragment.DaggerViewModelFragment
import com.example.multistreamaterialplaceholdercard.listeners.PlaceHolderViewListener
import com.example.multistreamhidescrollview.HideScrollView.Companion.RIGHT
import com.example.multistreamhidescrollview.animations.AlphaAnimation
import com.example.multistreamhidescrollview.animations.TranslationXAnimation
import com.google.android.material.snackbar.Snackbar
import com.ramotion.cardslider.CardSliderLayoutManager
import java.util.*
import javax.inject.Inject

class HomeFragment :
    DaggerViewModelFragment<HomeFragmentViewModel>(HomeFragmentViewModel::class.java),
    PlaceHolderViewListener {
    lateinit var binding: HomeFragmentBinding
    @Inject
    @SettingsPreferencesQualifier
    lateinit var settingsPreferences: SharedPreferences
    private lateinit var channelsViewPagerAdapter: PlaceHolderAdapter<DataItem, ChannelsViewPagerItemBinding>
    private lateinit var twitchChannelsAdapter: PlaceHolderAdapter<StreamsItem, ListItemTwoBinding>
    private lateinit var followingAdapter: PlaceHolderAdapter<StreamsItem, ListItemThreeBinding>
    private lateinit var topGamesAdapter: PlaceHolderAdapter<TopItem, ListItemFourBinding>
    private lateinit var translationXAnimation: TranslationXAnimation
    private lateinit var mixerTopGamesAdapter: PlaceHolderAdapter<MixerTopGames, ListItemFourBinding>
    private lateinit var mixerTopChannelsAdapter: PlaceHolderAdapter<MixerGameChannel, ListItemTwoBinding>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = HomeFragmentBinding.inflate(inflater, container, false)
        translationXAnimation = TranslationXAnimation(
            binding.hideScrollView,
            binding.hideScrollView.animationHandler.topDivider,
            binding.hideScrollView.animationHandler.bottomDivider
        )
        if (settingsPreferences.getBoolean(
                getString(R.string.twitch_visibility),
                true
            )
        ) setupTwitchUI() else hideTwitch()
        if (settingsPreferences.getBoolean(
                getString(R.string.mixer_visibility),
                true
            )
        ) setupMixerUI() else hideMixer()
        binding.hideScrollView.apply {
            val alphaAnimation =
                AlphaAnimation(this, animationHandler.topDivider, animationHandler.bottomDivider)
            addHiddenView(binding.homeText, RIGHT, alphaAnimation)
        }
        return binding.root
    }

    private fun setupViewPager() {
        channelsViewPagerAdapter =
            PlaceHolderAdapter(R.layout.channels_view_pager_item) { binding, item ->
                binding.viewPagerCard.cancelAnimation()
                binding.data = item
            }
        channelsViewPagerAdapter.setOnItemClickListener { i, v ->
            val item = channelsViewPagerAdapter.data?.get(i)
            item?.let {
                (requireActivity() as MainActivity).initLiveStreamPlayerFragment(
                    it.title,
                    it.user_name?.toLowerCase(Locale.getDefault()),
                    it.thumbnail_url,
                    it.user_name,
                    it.user?.display_name,
                    it.user_id ?: "",
                    it.viewer_count
                )
            }
        }
        val nextItemVisiblePx = resources.getDimension(R.dimen.viewpager_next_item_visible)
        val currentItemHorizontalMarginPx =
            resources.getDimension(R.dimen.viewpager_current_item_horizontal_margin)
        val pageTranslationX = nextItemVisiblePx + currentItemHorizontalMarginPx
        val pageTransformer = ViewPager2.PageTransformer { page: View, position: Float ->
            page.translationX = -pageTranslationX * position
            page.alpha = 1 - (0.70f * kotlin.math.abs(position))
            page.scaleY = 1 - (0.25f * kotlin.math.abs(position))
            page.scaleX = 1 - (0.10f * kotlin.math.abs(position))
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
        initTwitchTopGames()
        if (settingsPreferences.getBoolean(getString(R.string.twitch_sync), false)) {
            initViewPager()
            initTwitchFollowingLiveStream()
            initTwitchFollowedStreams()
        } else {
            binding.apply {
                twitchFollowingLiveStreamsText.hide()
                twitchFollowingChannelsText.hide()
                twitchFollowedLiveStreamsViewAllText.hide()
                twitchStreamersYouFollowViewAllText.hide()
                channelsViewPager.hide()
            }
        }
    }

    private fun initViewPager() {
        binding.apply {
            twitchText.visibility = View.VISIBLE
            twitchLogo.visibility = View.VISIBLE
            setupViewPager()
            //ViewPager
            viewModel.getChannels()
            viewModel.topChannelsLiveData.observe(viewLifecycleOwner) {
                channelsViewPagerAdapter.data = it
                if (channelsViewPagerAdapter.data != null && channelsViewPagerAdapter.data?.count()!! > 0) {
                    channelsViewPager.setCurrentItem(1, false)
                }
            }
        }
    }

    private fun initTwitchTopGames() {
        binding.apply {
            viewModel.getTopGames(10)
            viewModel.topGamesLiveData.observe(viewLifecycleOwner) {
                topGamesAdapter.data = it
                binding.twitchTopGamesList.scrollToPosition(1)
            }
            topGamesAdapter = PlaceHolderAdapter(
                R.layout.list_item_four,
                false
            ) { k, t ->
                k.viewersCount.text = NumbersConverter.getK(t.viewers, requireContext())
                ImageLoader.loadImageTwitchWithParams(k.gameBanner, t.game?.box?.large, 750, 400)
            }
            topGamesAdapter.onClickListener = object : PlaceHolderViewListener {

                override fun onClick(position: Int, itemView: View) {
                    val item = topGamesAdapter.data?.get(position)
                    val directions = HomeFragmentDirections.actionStartToTwitchGameCategory(item)
                    findNavController().navigate(directions)
                }

                override fun onCircleClick(position: Int, itemView: View) {
                }
            }
            twitchTopGamesList.apply {
                layoutManager = CardSliderLayoutManager(requireActivity())
                adapter = topGamesAdapter
                setHasFixedSize(true)
            }
            twitchTopGamesText.visibility = View.VISIBLE
        }
        binding.hideScrollView.addHiddenView(
            binding.topGamesViewAllText,
            RIGHT,
            translationXAnimation
        )
        binding.topGamesViewAllText.setOnClickListener {
            val directions = HomeFragmentDirections.actionHomeFragmentToTwitchGamesViewAllFragment()
            findNavController().navigate(directions)
        }
    }

    private fun initTwitchFollowedStreams() {
        binding.apply {
            viewModel.getFollowedStreams("all")
            viewModel.followedStreamsLiveData.observe(viewLifecycleOwner) {
                followingAdapter.data = it?.streams
            }
            followingAdapter =
                PlaceHolderAdapter(R.layout.list_item_three, false) { k, t ->
                    k.apply {
                        t.channel.also { channel ->
                            ImageLoader.loadImageTwitch(streamerBanner, channel?.logo)
                            streamerName.text =
                                channel?.name?.toUpperCase(Locale.getDefault())
                            ImageLoader.loadImage(
                                streamerProfileBanner,
                                t.channel?.logo
                            )
                        }
                    }
                }
            followingStreamsList.adapter = followingAdapter
            followingStreamsList.setHasFixedSize(true)
            followingAdapter.onClickListener = this@HomeFragment
            twitchFollowingChannelsText.visibility = View.VISIBLE
            twitchStreamersYouFollowViewAllText.visibility = View.VISIBLE
            twitchStreamersYouFollowViewAllText.setOnClickListener { findNavController().navigate(R.id.action_homeFragment_to_twitchChannelsAllViewFragment) }
            hideScrollView.addHiddenView(
                twitchStreamersYouFollowViewAllText,
                RIGHT,
                translationXAnimation
            )
        }
    }

    private fun initTwitchFollowingLiveStream() {
        binding.apply {
            viewModel.getFollowedLiveStreams("live")
            viewModel.followedLiveStreamsLiveData.observe(viewLifecycleOwner) {
                twitchChannelsAdapter.data = it?.streams
            }
            twitchChannelsAdapter =
                PlaceHolderAdapter(R.layout.list_item_two, false) { k, t ->
                    k.apply {
                        viewersCount.text = NumbersConverter.getK(t.viewers, requireContext())
                        ImageLoader.loadImageTwitch(streamImage, t.preview?.large)
                        t.channel.also { channel ->
                            streamTitle.text = channel?.status
                            ImageLoader.loadImageTwitch(streamerBanner, channel?.logo)
                            streamGame.text = channel?.game
                            streamerName.text = channel?.display_name
                        }
                    }
                }
            liveFollowingStreamsList.setHasFixedSize(true)
            liveFollowingStreamsList.adapter = twitchChannelsAdapter.also { placeHolderAdapter ->
                placeHolderAdapter.setOnItemClickListener(object : PlaceHolderViewListener {
                    override fun onClick(position: Int, itemView: View) {
                        Log.d(
                            "ITEMCLICK",
                            "width: ${itemView.width}, height: ${itemView.height},  left: ${itemView.left}, right: ${itemView.right}, top: ${itemView.top},  bottom: ${itemView.bottom}, x: ${itemView.x}, y: ${itemView.y}"
                        )
                        val item = placeHolderAdapter.data?.get(position)
                        item?.also {
                            (requireActivity() as MainActivity).initLiveStreamPlayerFragment(
                                it.channel?.status,
                                it.channel?.name,
                                it.channel?.logo,
                                it.game,
                                it.channel?.name,
                                it.channel?._id.toString(),
                                it.viewers
                            )
                        }
                    }

                    override fun onCircleClick(position: Int, itemView: View) {
                        val item = placeHolderAdapter.data?.get(position)
                        item?.also {
                            val directions =
                                HomeFragmentDirections.actionHomeFragmentToTwitchProfileFragment(it)
                            findNavController().navigate(directions)
                        }
                    }

                })
            }
            binding.hideScrollView.addHiddenView(
                binding.twitchFollowedLiveStreamsViewAllText,
                RIGHT,
                translationXAnimation
            )
            twitchFollowingLiveStreamsText.show()
            twitchFollowedLiveStreamsViewAllText.show()
            twitchFollowedLiveStreamsViewAllText.setOnClickListener { findNavController().navigate(R.id.action_homeFragment_to_twitchStreamsAllViewFragment) }
        }
    }


    private fun hideTwitch() {
        binding.apply {
            twitchFollowingChannelsText.hide()
            twitchFollowingLiveStreamsText.hide()
            twitchTopGamesText.hide()
            twitchText.hide()
            twitchLogo.hide()
            topGamesViewAllText.hide()
            twitchFollowedLiveStreamsViewAllText.hide()
            twitchStreamersYouFollowViewAllText.hide()
            topGamesViewAllText.hide()
            twitchTopGamesList.hide()
            followingStreamsList.hide()
            liveFollowingStreamsList.hide()
            channelsViewPager.hide()
        }
    }

    //Mixer
    private fun setupMixerUI() {
        initMixerTopGames()
        initMixerTopChannels()
    }

    private fun initMixerTopGames() {
        binding.apply {
            viewModel.getMixerTopGames(10)
            viewModel.mixerTopGamesLiveData.observe(viewLifecycleOwner) {
                mixerTopGamesAdapter.data = it
                mixerTopGamesList.scrollToPosition(1)
            }
            mixerTopGamesAdapter = PlaceHolderAdapter(
                R.layout.list_item_four,
                false
            ) { k, t ->
                k.viewersCount.text = NumbersConverter.getK(t.viewersCurrent, requireContext())
                ImageLoader.loadImage(k.gameBanner, t.coverUrl)
            }
            mixerTopGamesAdapter.onClickListener = object : PlaceHolderViewListener {

                override fun onClick(position: Int, itemView: View) {
                    val item = mixerTopGamesAdapter.data?.get(position)
                    val directions = HomeFragmentDirections.actionStartToMixerGameCategory(item)
                    findNavController().navigate(directions)
                }

                override fun onCircleClick(position: Int, itemView: View) {
                }
            }
            mixerTopGamesList.apply {
                layoutManager = CardSliderLayoutManager(requireActivity())
                adapter = mixerTopGamesAdapter
                setHasFixedSize(true)
            }
            mixerTopGamesText.visibility = View.VISIBLE
            mixerTopGamesViewAllText.setOnClickListener { findNavController().navigate(R.id.action_start_to_mixerTopGamesViewAllFragment) }
            binding.hideScrollView.addHiddenView(
                binding.mixerTopGamesViewAllText,
                RIGHT,
                translationXAnimation
            )
        }
    }

    private fun initMixerTopChannels() {
        binding.apply {
            viewModel.getMixerTopChannels(10)
            viewModel.mixerTopChannelsLiveData.observe(viewLifecycleOwner) {
                mixerTopChannelsAdapter.data = it
            }
            mixerTopChannelsAdapter = PlaceHolderAdapter(R.layout.list_item_two, false) { k, t ->
                k.apply {
                    streamTitle.text = t.name
                    ImageLoader.loadImageTwitch(streamImage,getMixerImageUrl(t.id))
                    ImageLoader.loadImage(streamerBanner, t.user?.avatarUrl )
                    streamerName.text = t.user?.username
                    viewersCount.text = NumbersConverter.getK(t.viewersCurrent, requireContext())
                    streamGame.text = t.name
                }
            }
            mixerTopChannelsList.setHasFixedSize(true)
            mixerTopChannelsList.adapter = mixerTopChannelsAdapter.also {
                it.setOnItemClickListener(object : PlaceHolderViewListener {
                    override fun onClick(position: Int, itemView: View) {
                        Snackbar.make(
                            binding.mixerTopChannelsList,
                            "Mixer streams player is not available right now",
                            Snackbar.LENGTH_SHORT
                        ).show()
                    }

                    override fun onCircleClick(position: Int, itemView: View) {
                        val item = mixerTopChannelsAdapter.data?.get(position)
                        val directions =
                            HomeFragmentDirections.actionHomeFragmentToMixerProfileFragment(item)
                        findNavController().navigate(directions)
                    }

                })
            }
            mixerTopChannelsViewAllText.setOnClickListener { findNavController().navigate(R.id.action_start_to_mixerChannelsAllViewFragment) }
            binding.hideScrollView.addHiddenView(
                binding.mixerTopChannelsViewAllText,
                RIGHT,
                translationXAnimation
            )
        }
    }

    private fun hideMixer() {
        binding.apply {
            mixerText.hide()
            mixerTopGamesViewAllText.hide()
            mixerTopChannelsViewAllText.hide()
            mixerLogo.hide()
            mixerTopGamesText.hide()
            mixerTopChannelsText.hide()
            mixerTopGamesList.hide()
            mixerTopChannelsList.hide()
        }
    }

    override fun onClick(position: Int, view: View) {
        val item = followingAdapter.data?.get(position)
        val directions =
            HomeFragmentDirections.actionHomeFragmentToTwitchProfileFragment(item)
        findNavController().navigate(directions)
    }

    override fun onCircleClick(position: Int, itemView: View) {

    }

}

fun View.hide() {
    this.visibility = View.GONE
}

fun View.show() {
    this.visibility = View.VISIBLE
}