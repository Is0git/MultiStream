package com.iso.multistream.ui.main_activity.fragments.view_all_fragments.twitch.streams_view_all

import android.view.View
import com.example.pagination.PageLoader
import com.iso.multistream.R
import com.iso.multistream.di.main_activity.main_fragments.view_all_fragments.ViewAllFragmentScope
import com.iso.multistream.network.twitch.models.v5.followed_streams.StreamsItem
import com.iso.multistream.ui.main_activity.fragments.view_all_fragments.ViewAllFragment
import com.iso.multistream.ui.main_activity.fragments.view_all_fragments.ViewAllListAdapter
import com.iso.multistream.ui.main_activity.fragments.view_all_fragments.ViewAllListAdapter.Companion.STREAMS
import com.iso.multistream.utils.HtmlParser
import com.iso.multistream.utils.NumbersConverter
import com.iso.multistream.utils.data_binding.ImageLoader

@ViewAllFragmentScope
class TwitchStreamsAllViewFragment : ViewAllFragment<TwitchStreamsViewAllViewModel, StreamsItem>(TwitchStreamsViewAllViewModel::class.java) {
    override fun onClick(position: Int, view: View) {

    }

    override fun onBind(): (ViewAllListAdapter.SearchViewHolder, StreamsItem?, Int) -> Unit {
        return { searchViewHolder, streamItem, i ->
            (searchViewHolder as ViewAllListAdapter.SearchViewHolder.StreamsViewHolder).apply {
                viewersText.text = NumbersConverter.getK(streamItem?.viewers, requireContext())
                text.text = streamItem?.channel?.status
                gameText.text = streamItem?.game
                ImageLoader.loadImageTwitchWithParams(image, streamItem?.preview?.medium, 125, 200)
            }
        }
    }

    override fun getPageLoader(): PageLoader<StreamsItem> {
       return viewModel.pageLoader
    }

    override fun getListType(): Int {
       return STREAMS
    }

    override fun getHeaderText(): CharSequence {
       return HtmlParser.getStringFromHtml(R.string.followed_live_streams_html, requireContext())
    }
}