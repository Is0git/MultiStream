package com.android.multistream.ui.fragments.home_fragment

import android.graphics.PixelFormat
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.viewpager2.widget.ViewPager2
import com.android.multistream.R
import com.android.multistream.databinding.ChannelsViewPagerItemBinding
import com.android.multistream.databinding.HomeFragmentBinding
import com.android.multistream.network.twitch.models.channels.DataItem
import com.android.multistream.ui.fragments.home_fragment.decorations.HorizontalMarginItemDecoration
import com.android.multistream.ui.fragments.home_fragment.view_model.HomeFragmentViewModel
import com.android.multistream.utils.PlaceHolderAdapter
import com.android.multistream.utils.ViewModelFactory
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class HomeFragment : DaggerFragment() {
    lateinit var binding: HomeFragmentBinding
    @Inject
    lateinit var factory: ViewModelFactory
    lateinit var channelsViewPagerAdapter: PlaceHolderAdapter<DataItem, ChannelsViewPagerItemBinding>
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
        return binding.root
    }

    fun setupViewPager() {
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

    fun observe() {
        viewModel.topChannelsLiveData.observe(viewLifecycleOwner, Observer {
            channelsViewPagerAdapter.data = it
        })
    }
}