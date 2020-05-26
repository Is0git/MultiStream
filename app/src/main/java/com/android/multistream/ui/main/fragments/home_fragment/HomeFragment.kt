package com.android.multistream.ui.main.fragments.home_fragment

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.android.multistream.R
import com.android.multistream.databinding.*
import com.android.multistream.di.qualifiers.SettingsPreferencesQualifier
import com.android.multistream.network.mixer.models.mixer_channels.MixerGameChannel
import com.android.multistream.network.twitch.models.new_twitch_api.channels.DataItem
import com.android.multistream.network.twitch.models.new_twitch_api.top_games.TopGame
import com.android.multistream.network.twitch.models.v5.followed_streams.StreamsItem
import com.android.multistream.ui.main_activity.MainActivity
import com.android.multistream.ui.main_activity.MainActivityViewModel
import com.android.multistream.ui.main.fragments.home_fragment.decorations.HorizontalMarginItemDecoration
import com.android.multistream.ui.main.fragments.home_fragment.view_model.HomeFragmentViewModel
import com.android.multistream.utils.NumbersConverter
import com.android.multistream.utils.PlaceHolderAdapter
import com.android.multistream.utils.data_binding.ImageLoader
import com.example.daggerviewmodelfragment.DaggerViewModelFragment
import com.example.multistreamaterialplaceholdercard.listeners.PlaceHolderViewListener
import com.example.multistreamhidescrollview.HideScrollView
import com.example.multistreamhidescrollview.HideScrollView.Companion.RIGHT
import com.example.multistreamhidescrollview.animations.AlphaAnimation
import com.example.multistreamhidescrollview.animations.TranslationXAnimation
import com.multistream.multistreamsearchview.search_view.OnItemClickListener
import com.ramotion.cardslider.CardSliderLayoutManager
import kotlinx.android.synthetic.main.list_item_three.*
import kotlinx.android.synthetic.main.profile_content.*
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
    private lateinit var topGamesAdapter: PlaceHolderAdapter<TopGame, ListItemFourBinding>
    private lateinit var mainActivityViewModel: MainActivityViewModel
    private lateinit var translationXAnimation: TranslationXAnimation

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = HomeFragmentBinding.inflate(inflater, container, false)
        mainActivityViewModel = ViewModelProvider(requireActivity()).get(MainActivityViewModel::class.java)
        if (settingsPreferences.getBoolean(
                getString(R.string.twitch_visibility),
                false
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

//        binding.homeText.setOnClickListener {
//            HomeFragmentDirections.actionHomeFragmentToMixerProfileFragment(
//                MixerGameChannel(id = 6020650)
//            ).also { navController.navigate(it) }
//        }
        (requireActivity() as MainActivity).apply {
            showActionBar()
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        (activity as MainActivity).initNavigationDrawer()
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
                (requireActivity() as MainActivity).createPlayerFragment(
                    it.title,
                    it.user_name,
                    it.thumbnail_url,
                    it.user_name,
                    it.user?.displayName
                )
            }
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
        initViewPager()
        initTwitchTopGames()
        if (settingsPreferences.getBoolean(getString(R.string.twitch_sync), true)) {
            initTwitchFollowingLiveStream()
            initTwitchFollowedStreams()
        } else {
            binding.apply {
                twitchFollowingLiveStreamsText.visibility = View.GONE
                twitchFollowingChannelsText.visibility = View.GONE
                twitchFollowedLiveStreamsViewAllText.visibility = View.GONE
                twitchStreamersYouFollowViewAllText.visibility = View.GONE
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
                ImageLoader.loadImageTwitchWithParams(k.gameBanner, t.box_art_url, 750, 400)
            }
            topGamesAdapter.onClickListener = object : PlaceHolderViewListener {

                override fun onClick(position: Int, itemView: View) {

                }
            }
            twitchTopGamesList.apply {
                layoutManager = CardSliderLayoutManager(requireActivity())
                adapter = topGamesAdapter
            }
            twitchTopGamesText.visibility = View.VISIBLE
        }
        translationXAnimation = TranslationXAnimation(
            binding.hideScrollView,
            binding.hideScrollView.animationHandler.topDivider,
            binding.hideScrollView.animationHandler.bottomDivider
        )
        binding.hideScrollView.addHiddenView(
            binding.topGamesViewAllText,
            RIGHT,
            translationXAnimation
        )
        binding.topGamesViewAllText.setOnClickListener {
            val directions = HomeFragmentDirections.actionHomeFragmentToTwitchGamesViewAllFragment()
            findNavController().navigate(directions)
        }
//        addHiddenView(binding.twitchStreamersYouFollowViewAllText, RIGHT, translationXAnimation)
    }

    private fun initTwitchFollowedStreams() {
        binding.apply {
            viewModel.getFollowedStreams("all")
            viewModel.followedStreamsLiveData.observe(viewLifecycleOwner) {
                followingAdapter.data = it?.streams
            }
            followingAdapter =
                PlaceHolderAdapter(R.layout.list_item_three, true) { k, t ->
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
            followingAdapter.onClickListener = this@HomeFragment
            twitchFollowingChannelsText.visibility = View.VISIBLE
            twitchStreamersYouFollowViewAllText.visibility = View.VISIBLE
            twitchStreamersYouFollowViewAllText.setOnClickListener { findNavController().navigate(R.id.action_homeFragment_to_twitchChannelsAllViewFragment) }
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
                            streamerName.text = channel?.name
                        }
                    }
                }
            liveFollowingStreamsList.adapter = twitchChannelsAdapter.also {
                it.setOnItemClickListener { i, v ->
                    val item = it.data?.get(i)
                    item?.also {
                        (requireActivity() as MainActivity).createPlayerFragment(
                            item.channel?.status,
                            item.channel?.name,
                            item.channel?.logo,
                            item.game,
                            item.channel?.name
                        )
                    }
                }
            }
            binding.hideScrollView.addHiddenView(
                binding.twitchFollowedLiveStreamsViewAllText,
                RIGHT,
                translationXAnimation
            )
            twitchFollowingLiveStreamsText.visibility = View.VISIBLE
            twitchFollowedLiveStreamsViewAllText.visibility = View.VISIBLE
            twitchFollowedLiveStreamsViewAllText.setOnClickListener { findNavController().navigate(R.id.action_homeFragment_to_twitchStreamsAllViewFragment) }
        }
    }

    private fun hideTwitch() {
        binding.apply {
            twitchFollowingChannelsText.visibility = View.GONE
            twitchFollowingLiveStreamsText.visibility = View.GONE
            twitchTopGamesText.visibility = View.GONE
            twitchText.visibility = View.GONE
            twitchLogo.visibility = View.GONE
            topGamesViewAllText.visibility = View.GONE
            twitchFollowedLiveStreamsViewAllText.visibility = View.GONE
            twitchStreamersYouFollowViewAllText.visibility = View.GONE
        }
    }

    //Mixer
    private fun setupMixerUI() {}

    private fun hideMixer() {
        binding.apply {
            mixerText.visibility = View.GONE
            mixerRecommendedChannels.visibility = View.GONE
            mixerTopChannelsText.visibility = View.GONE
        }
    }

    override fun onClick(position: Int, view: View) {
        val item = followingAdapter.data?.get(position)
        val directions =
            HomeFragmentDirections.actionHomeFragmentToTwitchProfileFragment(item?.channel)
        findNavController().navigate(directions)
    }
}