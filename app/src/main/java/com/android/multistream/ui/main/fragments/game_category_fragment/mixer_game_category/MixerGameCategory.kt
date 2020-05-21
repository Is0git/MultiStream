package com.android.multistream.ui.main.fragments.game_category_fragment.mixer_game_category

import android.view.View
import androidx.navigation.fragment.navArgs
import com.android.multistream.R
import com.android.multistream.databinding.ListItemTwoExtendedBinding
import com.android.multistream.network.mixer.models.mixer_channels.MixerGameChannel
import com.android.multistream.ui.main.fragments.game_category_fragment.GameCategoryFragment
import com.android.multistream.utils.NumbersConverter
import com.android.multistream.utils.PlaceHolderAdapter
import com.android.multistream.utils.data_binding.ImageLoader
import com.android.multistream.utils.getMixerImageUrl
import com.example.pagination.PageLoader
import kotlinx.android.synthetic.main.game_content.view.*
import kotlinx.android.synthetic.main.profile_content.*

class MixerGameCategory : GameCategoryFragment<MixerGameChannel, MixerGameCategoryViewModel>(MixerGameCategoryViewModel::class.java){

    private val args: MixerGameCategoryArgs by navArgs()

    override fun getPlaceHolderAdapter(): PlaceHolderAdapter<MixerGameChannel, ListItemTwoExtendedBinding> {
        return  PlaceHolderAdapter(R.layout.list_item_two_extended, false) { k, t ->
            k.apply {
                streamTitle.text = t.name
                ImageLoader.loadImageTwitch(streamImage, t.user?.avatarUrl)
                ImageLoader.loadImage(streamerBanner, getMixerImageUrl(t.id))
                streamerName.text = t.user?.username
                viewersCount.text = NumbersConverter.getK(t.viewersCurrent, requireContext())
                streamGame.text = args.mixerGame?.name
            }
        }
    }

    override fun onCardClick(position: Int, view: View) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getPageLoader(): PageLoader<MixerGameChannel> {
        return viewModel.pageLoader
    }

    override fun setArgs() {
        args.mixerGame?.id?.also {
            viewModel.repo.id = it
        }
        binding.headerLayout.apply {
            gameName.text = args.mixerGame?.name
            followersCount.text = getString(R.string.followers, args.mixerGame?.online)
            viewersCount.text = getString(R.string.viewers, args.mixerGame?.viewersCurrent)
            ImageLoader.loadImage(gameBg, args.mixerGame?.backgroundUrl)
        }
    }
}