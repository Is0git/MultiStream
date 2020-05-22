package com.android.multistream.ui.main.fragments.view_all_fragments.twitch.channels_view_all

import android.view.View
import com.android.multistream.R
import com.android.multistream.network.twitch.models.v5.followed_streams.StreamsItem
import com.android.multistream.ui.main.fragments.view_all_fragments.ViewAllFragment
import com.android.multistream.ui.main.fragments.view_all_fragments.ViewAllListAdapter
import com.android.multistream.ui.main.fragments.view_all_fragments.twitch.streams_view_all.TwitchStreamsViewAllViewModel
import com.android.multistream.utils.HtmlParser
import com.android.multistream.utils.NumbersConverter
import com.android.multistream.utils.data_binding.ImageLoader
import com.example.pagination.PageLoader
import kotlinx.android.synthetic.main.profile_content.*

class TwitchChannelsAllViewFragment : ViewAllFragment<TwitchChannelsViewAllViewModel, StreamsItem>(
    TwitchChannelsViewAllViewModel::class.java) {
    override fun onClick(position: Int, view: View) {

    }

    override fun onBind(): (ViewAllListAdapter.SearchViewHolder, StreamsItem?, Int) -> Unit {
        return { searchViewHolder, channelItem, i ->
            (searchViewHolder as ViewAllListAdapter.SearchViewHolder.ChannelsViewHolder).apply {
                viewersText.text = NumbersConverter.getK(channelItem?.viewers, requireContext())
                followersCount.text = NumbersConverter.getK(channelItem?.channel?.followers, requireContext())
                text.text = channelItem?.channel?.display_name
                ImageLoader.loadImage(image, channelItem?.channel?.logo)
            }
        }
    }

    override fun getPageLoader(): PageLoader<StreamsItem> {
        return viewModel.pageLoader
    }

    override fun getListType(): Int {
        return ViewAllListAdapter.CHANNELS
    }

    override fun getHeaderText(): CharSequence {
        return HtmlParser.getStringFromHtml(R.string.followed_channels_html, requireContext())
    }
}