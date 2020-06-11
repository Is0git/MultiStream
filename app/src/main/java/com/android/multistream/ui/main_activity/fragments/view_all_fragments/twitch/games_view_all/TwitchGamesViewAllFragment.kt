package com.android.multistream.ui.main_activity.fragments.view_all_fragments.twitch.games_view_all

import android.view.View
import androidx.navigation.fragment.findNavController
import com.android.multistream.R
import com.android.multistream.network.twitch.models.v5.top_games.TopItem
import com.android.multistream.ui.main_activity.fragments.home_fragment.HomeFragmentDirections
import com.android.multistream.ui.main_activity.fragments.view_all_fragments.ViewAllFragment
import com.android.multistream.ui.main_activity.fragments.view_all_fragments.ViewAllListAdapter
import com.android.multistream.utils.HtmlParser
import com.android.multistream.utils.NumbersConverter
import com.android.multistream.utils.data_binding.ImageLoader
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
        val item = viewAllListAdapter.data?.get(position)
        val directions = TwitchGamesViewAllFragmentDirections.actionTwitchGamesViewAllFragmentToTwitchGameCategory(item)
        findNavController().navigate(directions)
    }

    override fun getHeaderText(): CharSequence {
        return HtmlParser.getStringFromHtml(R.string.top_games_html, requireContext())
    }
}