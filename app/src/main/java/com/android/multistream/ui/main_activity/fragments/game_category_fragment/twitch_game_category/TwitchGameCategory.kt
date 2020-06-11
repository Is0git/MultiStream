package com.android.multistream.ui.main_activity.fragments.game_category_fragment.twitch_game_category

import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.android.multistream.R
import com.android.multistream.databinding.ListItemTwoExtendedBinding
import com.android.multistream.network.twitch.models.new_twitch_api.channels.DataItem
import com.android.multistream.network.twitch.models.v5.followed_streams.Channel
import com.android.multistream.network.twitch.models.v5.followed_streams.StreamsItem
import com.android.multistream.ui.main_activity.MainActivity
import com.android.multistream.ui.main_activity.fragments.game_category_fragment.GameCategoryFragment
import com.android.multistream.utils.NumbersConverter
import com.android.multistream.utils.PlaceHolderAdapter
import com.android.multistream.utils.data_binding.ImageLoader
import com.example.pagination.PageLoader
import kotlinx.android.synthetic.main.game_content.view.*

class TwitchGameCategory :
    GameCategoryFragment<DataItem, TwitchGameCategoryViewModel>(TwitchGameCategoryViewModel::class.java) {

    private val args: TwitchGameCategoryArgs by navArgs()

    override fun getPlaceHolderAdapter(): PlaceHolderAdapter<DataItem, ListItemTwoExtendedBinding> {
        return PlaceHolderAdapter(R.layout.list_item_two_extended, false) { k, t ->
            k.apply {
                streamTitle.text = t.title
                ImageLoader.loadImageTwitchWithParams(streamImage, t.thumbnail_url, 350, 660)
                ImageLoader.loadImage(streamerBanner, t.user?.logo)
                streamerName.text = t.user_name
                viewersCount.text = NumbersConverter.getK(t.viewer_count, requireContext())
                streamGame.text = args.twitchGame?.game?.name
            }
        }
    }

    override fun getPageLoader(): PageLoader<DataItem> {
        return viewModel.pageLoader
    }

    override fun onClick(position: Int, itemView: View) {
        val item = adapter.data?.get(position)
        item?.also {
            (requireActivity() as MainActivity).initLiveStreamPlayerFragment(
                it.title,
                it.user?.name,
                it.user?.logo,
                it.type,
                it.user_name,
                it.user_id.toString(),
                it.viewer_count
            )
        }
    }

    override fun onCircleClick(position: Int, itemView: View) {
        val item = adapter.data?.get(position)
        val streamItems =
            StreamsItem(
                null,
                null,
                null,
                null,
                item?.viewer_count,
                null,
                null,
                Channel(
                    null,
                    null,
                    null,
                    null,
                    null,
                    item?.user?.name,
                    null,
                    _id = item?.user_id?.toInt()
                )
            )
       val direction = TwitchGameCategoryDirections.actionTwitchGameCategoryToTwitchProfileFragment(streamItems)
        findNavController().navigate(direction)
    }

    override fun setArgs() {
        args.twitchGame?.game?._id?.also {
            viewModel.repository.gameId = it
        }

        binding.headerLayout.apply {
            gameName.text = args.twitchGame?.game?.name
            followersCount.text = getString(R.string.channels, args.twitchGame?.channels)
            viewersCount.text = getString(R.string.viewers, args.twitchGame?.viewers)
            ImageLoader.loadImageTwitchWithParams(
                gameBg,
                args.twitchGame?.game?.box?.large,
                300,
                660
            )
        }
    }
}