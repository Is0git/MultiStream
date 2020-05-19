package com.android.multistream.ui.main.fragments.game_category_fragment.twitch_game_category

import android.view.View
import androidx.navigation.fragment.navArgs
import com.android.multistream.R
import com.android.multistream.databinding.ListItemTwoExtendedBinding
import com.android.multistream.network.twitch.models.new_twitch_api.channels.DataItem
import com.android.multistream.ui.main.fragments.game_category_fragment.GameCategoryFragment
import com.android.multistream.utils.PlaceHolderAdapter
import com.android.multistream.utils.data_binding.ImageLoader
import com.example.pagination.PageLoader
import kotlinx.android.synthetic.main.game_content.view.*
import kotlinx.android.synthetic.main.profile_content.*

class TwitchGameCategory :
    GameCategoryFragment<DataItem, TwitchGameCategoryViewModel>(TwitchGameCategoryViewModel::class.java) {

    private val args: TwitchGameCategoryArgs by navArgs()

    override fun getPlaceHolderAdapter(): PlaceHolderAdapter<DataItem, ListItemTwoExtendedBinding> {
        return PlaceHolderAdapter(R.layout.list_item_two_extended, false) { k, t ->
            k.apply {
                streamTitle.text = t.title
                ImageLoader.loadImageTwitch(streamImage, t.thumbnail_url)
                ImageLoader.loadImage(streamerBanner, t.user?.logo)
                streamerName.text = t.user_name
                viewersCount.text = t.viewer_count.toString()
                streamGame.text = args.twitchGame?.game?.name
            }
        }
    }

    override fun onCardClick(position: Int, view: View) {

    }

    override fun getPageLoader(): PageLoader<DataItem> {
        return viewModel.pageLoader
    }

    override fun setArgs() {
        args.twitchGame?.game?._id?.also {
            viewModel.repository.gameId = it
        }

        binding.headerLayout.apply {
            gameName.text = args.twitchGame?.game?.name
            followersCount.text = getString(R.string.followers, args.twitchGame?.channels)
            viewersCount.text = getString(R.string.viewers, args.twitchGame?.viewers)
            ImageLoader.loadImageTwitch(gameBg, args.twitchGame?.game?.box?.medium)
        }
    }
}