package com.iso.multistream.ui.main_activity.fragments.game_category_fragment.mixer_game_category

import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.pagination.PageLoader
import com.google.android.material.snackbar.Snackbar
import com.iso.multistream.R
import com.iso.multistream.databinding.ListItemTwoExtendedBinding
import com.iso.multistream.network.mixer.models.mixer_channels.MixerGameChannel
import com.iso.multistream.ui.main_activity.fragments.game_category_fragment.GameCategoryFragment
import com.iso.multistream.utils.NumbersConverter
import com.iso.multistream.utils.PlaceHolderAdapter
import com.iso.multistream.utils.data_binding.ImageLoader
import com.iso.multistream.utils.getMixerImageUrl
import kotlinx.android.synthetic.main.game_content.view.*

class MixerGameCategory :
    GameCategoryFragment<MixerGameChannel, MixerGameCategoryViewModel>(MixerGameCategoryViewModel::class.java) {

    private val args: MixerGameCategoryArgs by navArgs()

    override fun getPlaceHolderAdapter(): PlaceHolderAdapter<MixerGameChannel, ListItemTwoExtendedBinding> {
        return PlaceHolderAdapter(R.layout.list_item_two_extended, false) { k, t ->
            k.apply {
                streamTitle.text = t.name
                ImageLoader.loadImageTwitch(streamImage, getMixerImageUrl(t.id))
                ImageLoader.loadImage(streamerBanner, t.user?.avatarUrl)
                streamerName.text = t.user?.username
                viewersCount.text = NumbersConverter.getK(t.viewersCurrent, requireContext())
                streamGame.text = args.mixerGame?.name
            }
        }
    }

    override fun getPageLoader(): PageLoader<MixerGameChannel> {
        return viewModel.pageLoader
    }

    override fun onClick(position: Int, itemView: View) {
        Snackbar.make(
            binding.channelsRecyclerview,
            "Mixer streams player is not available right now",
            Snackbar.LENGTH_SHORT
        ).show()
    }

    override fun onCircleClick(position: Int, itemView: View) {
        val item = adapter.data?.get(position)
        val direction =
            MixerGameCategoryDirections.actionMixerGameCategoryToMixerProfileFragment(item)
        findNavController().navigate(direction)
    }

    override fun setArgs() {
        args.mixerGame?.id?.also {
            viewModel.repo.id = it
        }
        binding.headerLayout.apply {
            gameName.text = args.mixerGame?.name
            followersCount.text = getString(R.string.channels, args.mixerGame?.online)
            viewersCount.text = getString(R.string.viewers, args.mixerGame?.viewersCurrent)
            ImageLoader.loadImage(gameBg, args.mixerGame?.backgroundUrl)
        }
    }
}