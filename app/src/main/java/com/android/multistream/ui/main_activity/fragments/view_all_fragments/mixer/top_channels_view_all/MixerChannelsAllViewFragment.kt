package com.android.multistream.ui.main_activity.fragments.view_all_fragments.mixer.top_channels_view_all

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.android.multistream.R
import com.android.multistream.network.mixer.models.mixer_channels.MixerGameChannel
import com.android.multistream.ui.main_activity.MainActivityViewModel
import com.android.multistream.ui.main_activity.fragments.view_all_fragments.ViewAllFragment
import com.android.multistream.ui.main_activity.fragments.view_all_fragments.ViewAllListAdapter
import com.android.multistream.utils.HtmlParser
import com.android.multistream.utils.NumbersConverter
import com.android.multistream.utils.data_binding.ImageLoader
import com.example.pagination.PageLoader
import com.multistream.multistreamsearchview.search_view.OnItemClickListener

class MixerChannelsAllViewFragment : ViewAllFragment<MixerTopChannelsViewAllViewModel, MixerGameChannel>(
    MixerTopChannelsViewAllViewModel::class.java
) {

    lateinit var mainActivityViewModel: MainActivityViewModel

    override fun onClick(position: Int, view: View) {
        val item = viewAllListAdapter.data?.get(position)
        val directions = MixerChannelsAllViewFragmentDirections.actionMixerChannelsAllViewFragmentToMixerProfileFragment(item)
        findNavController().navigate(directions)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainActivityViewModel =
            ViewModelProvider(requireActivity()).get(MainActivityViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewAllListAdapter.onFollowButtonClickListener = object : OnItemClickListener {
            override fun onClick(position: Int, view: View) {
                view.isSelected = !view.isSelected
            }
        }
    }

    override fun onBind(): (ViewAllListAdapter.SearchViewHolder, MixerGameChannel?, Int) -> Unit {
        return { searchViewHolder, channelItem, i ->
            (searchViewHolder as ViewAllListAdapter.SearchViewHolder.ChannelsViewHolder).apply {
                followButton.visibility = View.GONE
                viewersText.text = NumbersConverter.getK(channelItem?.viewersCurrent, requireContext())
                followersCount.text =
                    NumbersConverter.getK(channelItem?.numFollowers, requireContext())
                text.text = channelItem?.name
                ImageLoader.loadImage(image, channelItem?.thumbnail?.url)
            }
        }
    }

    override fun getPageLoader(): PageLoader<MixerGameChannel> {
        return viewModel.pageLoader
    }

    override fun getListType(): Int {
        return ViewAllListAdapter.CHANNELS
    }

    override fun onDestroyView() {
        binding.viewAllList.layoutManager?.apply {
            for (a in 0 until childCount) {
                val child = getChildAt(a)
                val followButton = child?.findViewById<View>(R.id.follow_button)
                if (followButton != null && !followButton.isSelected) {
                    val id = viewAllListAdapter.data?.get(a)?.id
                    mainActivityViewModel.unFollowTwitchUser(id.toString())
                }
            }
        }
        super.onDestroyView()
    }

    override fun getHeaderText(): CharSequence {
        return HtmlParser.getStringFromHtml(R.string.top_channels_html, requireContext())
    }

}