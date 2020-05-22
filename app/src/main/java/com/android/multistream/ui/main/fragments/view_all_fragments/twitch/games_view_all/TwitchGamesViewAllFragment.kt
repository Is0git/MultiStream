package com.android.multistream.ui.main.fragments.view_all_fragments.twitch.games_view_all

import android.view.View
import com.android.multistream.R
import com.android.multistream.network.twitch.models.v5.top_games.TopItem
import com.android.multistream.ui.main.fragments.view_all_fragments.ViewAllFragment
import com.android.multistream.ui.main.fragments.view_all_fragments.ViewAllListAdapter
import com.android.multistream.utils.HtmlParser
import com.android.multistream.utils.NumbersConverter
import com.android.multistream.utils.data_binding.ImageLoader
import com.bumptech.glide.Glide
import com.example.pagination.PageLoader
import com.multistream.multistreamsearchview.search_view.SearchViewLayout.Companion.GAMES

class TwitchGamesViewAllFragment :
    ViewAllFragment<TwitchGamesViewAllViewModel, TopItem>(TwitchGamesViewAllViewModel::class.java) {
    override fun onBind(): (ViewAllListAdapter.SearchViewHolder, TopItem?, Int) -> Unit {
        return { searchViewHolder, topItem, i ->
            (searchViewHolder as ViewAllListAdapter.SearchViewHolder.GamesViewHolder).apply {
                this.viewerCount.text = NumbersConverter.getK(topItem?.viewers, requireContext())
                text.text = topItem?.game?.name
                channelsCount.text = NumbersConverter.getK(topItem?.channels, requireContext())
                ImageLoader.loadImageTwitchWithParams(image, topItem?.game?.box?.medium, 200, 125)
            }
        }
    }

    override fun getPageLoader(): PageLoader<TopItem> {
       return viewModel.pageLoader
    }

    override fun getListType(): Int {
        return GAMES
    }

    override fun onClick(position: Int, view: View) {

    }

    override fun getHeaderText(): CharSequence {
        return HtmlParser.getStringFromHtml(R.string.top_games_html, requireContext())
    }
}