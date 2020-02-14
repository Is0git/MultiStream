package com.android.multistream.ui.fragments.home_fragment

import android.graphics.PixelFormat
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.marginEnd
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.viewpager2.widget.ViewPager2
import com.android.multistream.R
import com.android.multistream.databinding.ChannelsViewPagerItemBinding
import com.android.multistream.databinding.HomeFragmentBinding
import com.android.multistream.databinding.ListItemOneBinding
import com.android.multistream.network.twitch.models.Data
import com.android.multistream.network.twitch.models.channels.DataItem
import com.android.multistream.ui.fragments.home_fragment.decorations.HorizontalMarginItemDecoration
import com.android.multistream.ui.fragments.home_fragment.view_model.HomeFragmentViewModel
import com.android.multistream.ui.widgets.hide_scroll_view.HideScrollView.Companion.LEFT
import com.android.multistream.ui.widgets.hide_scroll_view.HideScrollView.Companion.RIGHT
import com.android.multistream.utils.PlaceHolderAdapter
import com.android.multistream.utils.ViewModelFactory
import com.google.android.material.textview.MaterialTextView
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.title_material_card.view.*
import javax.inject.Inject

class HomeFragment : DaggerFragment() {
    lateinit var binding: HomeFragmentBinding
    @Inject
    lateinit var factory: ViewModelFactory
    lateinit var channelsViewPagerAdapter: PlaceHolderAdapter<DataItem, ChannelsViewPagerItemBinding>
    lateinit var twitchChannelsAdapter: PlaceHolderAdapter<DataItem, ListItemOneBinding>
    lateinit var viewModel: HomeFragmentViewModel
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = HomeFragmentBinding.inflate(inflater, container, false)
        viewModel = ViewModelProviders.of(this, factory).get(HomeFragmentViewModel::class.java)
        setupViewPager()
        observe()
        setupLists()

        binding.hideScrollView.apply {
            addHiddenView(binding.homeText, RIGHT)
            addHiddenView(binding.twitchText, RIGHT)
            addHiddenView(binding.twitchRecommendedChannels, LEFT)
            addHiddenView(binding.twitchRecommendedChannelsList, RIGHT)
            addHiddenView(binding.twitchTopChannelsText, LEFT)
            addHiddenView(binding.twitchTopChannelsList, RIGHT)
            addHiddenView(binding.mixerText, LEFT)
            addHiddenView(binding.mixerRecommendedChannels, RIGHT)
            addHiddenView(binding.mixerRecommendedChannelsList, LEFT)
            addHiddenView(binding.mixerTopChannelsText, RIGHT)
            addHiddenView(binding.mixerTopChannelsList, LEFT)
        }
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
            // Next line scales the item's height. You can remove it if you don't want this effect
            page.scaleY = 1 - (0.25f * kotlin.math.abs(position))
            // If you want a fading effect uncomment the next line:

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
        twitchChannelsAdapter = PlaceHolderAdapter(R.layout.list_item_one, false) {k, t ->  }
        binding.twitchRecommendedChannelsList.adapter = twitchChannelsAdapter
        binding.twitchTopChannelsList.adapter = PlaceHolderAdapter<Data, ListItemOneBinding>(R.layout.list_item_one, true) {k, t ->  }
        binding.mixerRecommendedChannelsList.adapter  = PlaceHolderAdapter<Data, ListItemOneBinding>(R.layout.list_item_one, true) {k, t ->  }
        binding.mixerTopChannelsList.adapter = PlaceHolderAdapter<Data, ListItemOneBinding>(R.layout.list_item_one, false) {k, t ->  }


    }

    private fun observe() {
        viewModel.topChannelsLiveData.observe(viewLifecycleOwner, Observer {
            channelsViewPagerAdapter.data = it
        })
    }
}