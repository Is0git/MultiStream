package com.android.multistream.ui.main.fragments.view_all_fragments.twitch.channels_view_all

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.multistream.R
import com.android.multistream.network.twitch.models.v5.followed_streams.StreamsItem
import com.android.multistream.ui.main.fragments.view_all_fragments.ViewAllFragment
import com.android.multistream.ui.main.fragments.view_all_fragments.ViewAllListAdapter
import com.android.multistream.ui.main.fragments.view_all_fragments.twitch.streams_view_all.TwitchStreamsViewAllViewModel
import com.android.multistream.ui.main_activity.MainActivityViewModel
import com.android.multistream.utils.HtmlParser
import com.android.multistream.utils.NumbersConverter
import com.android.multistream.utils.data_binding.ImageLoader
import com.example.pagination.PageLoader
import com.multistream.multistreamsearchview.search_view.OnItemClickListener
import kotlinx.android.synthetic.main.profile_content.*

class TwitchChannelsAllViewFragment : ViewAllFragment<TwitchChannelsViewAllViewModel, StreamsItem>(
    TwitchChannelsViewAllViewModel::class.java
) {

    lateinit var mainActivityViewModel: MainActivityViewModel

    override fun onClick(position: Int, view: View) {

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
                Log.d("POSITIONTAG", position.toString())
//                val id = viewAllListAdapter.data?.get(position)?.channel?._id.toString()
//                if (view.isSelected) {
//                    mainActivityViewModel.followTwitchUser(id)
//                } else {
//                    mainActivityViewModel.unFollowTwitchUser(id)
//                }
                view.isSelected = !view.isSelected
            }
        }
    }

    override fun onBind(): (ViewAllListAdapter.SearchViewHolder, StreamsItem?, Int) -> Unit {
        return { searchViewHolder, channelItem, i ->
            (searchViewHolder as ViewAllListAdapter.SearchViewHolder.ChannelsViewHolder).apply {
                viewersText.text = NumbersConverter.getK(channelItem?.viewers, requireContext())
                followersCount.text =
                    NumbersConverter.getK(channelItem?.channel?.followers, requireContext())
                text.text = channelItem?.channel?.display_name
                ImageLoader.loadImage(image, channelItem?.channel?.logo)
            }
        }
    }

    override fun getPageLoader(): PageLoader<StreamsItem> {
        return mainActivityViewModel.twitchFollowingChannelsPageLoader
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
                    val id = viewAllListAdapter.data?.get(a)?.channel?._id
                    mainActivityViewModel.unFollowTwitchUser(id.toString())
                }
            }
        }
        super.onDestroyView()
    }

    override fun getHeaderText(): CharSequence {
        return HtmlParser.getStringFromHtml(R.string.followed_channels_html, requireContext())
    }

}