package com.android.multistream.ui.main_activity.fragments.view_all_fragments.mixer.top_games_view_all

import android.view.View
import androidx.navigation.fragment.findNavController
import com.android.multistream.R
import com.android.multistream.network.mixer.models.top_games.MixerTopGames
import com.android.multistream.network.twitch.models.v5.top_games.TopItem
import com.android.multistream.ui.main_activity.fragments.view_all_fragments.ViewAllFragment
import com.android.multistream.ui.main_activity.fragments.view_all_fragments.ViewAllListAdapter
import com.android.multistream.ui.main_activity.fragments.view_all_fragments.twitch.games_view_all.TwitchGamesViewAllViewModel
import com.android.multistream.utils.HtmlParser
import com.android.multistream.utils.NumbersConverter
import com.android.multistream.utils.data_binding.ImageLoader
import com.example.pagination.PageLoader
import com.multistream.multistreamsearchview.search_view.SearchViewLayout

class MixerTopGamesViewAllFragment : ViewAllFragment<MixerTopGamesViewAllViewModel, MixerTopGames>(MixerTopGamesViewAllViewModel::class.java) {
    override fun onBind(): (ViewAllListAdapter.SearchViewHolder, MixerTopGames?, Int) -> Unit {
        return { searchViewHolder, topItem, i ->
            (searchViewHolder as ViewAllListAdapter.SearchViewHolder.GamesViewHolder).apply {
                this.viewerCount.text = NumbersConverter.getK(topItem?.viewersCurrent, requireContext())
                text.text = topItem?.name
                channelsCount.text = NumbersConverter.getK(topItem?.viewersCurrent, requireContext())
                ImageLoader.loadImage(image,topItem?.coverUrl)
            }
        }
    }

    override fun getPageLoader(): PageLoader<MixerTopGames> {
        return viewModel.pageLoader
    }

    override fun getListType(): Int {
        return SearchViewLayout.GAMES
    }

    override fun onClick(position: Int, view: View) {
        val item = viewAllListAdapter.data?.get(position)
        val directions = MixerTopGamesViewAllFragmentDirections.actionMixerTopGamesViewAllFragmentToMixerGameCategory(item)
        findNavController().navigate(directions)
    }

    override fun getHeaderText(): CharSequence {
        return HtmlParser.getStringFromHtml(R.string.top_games_html, requireContext())
    }
}