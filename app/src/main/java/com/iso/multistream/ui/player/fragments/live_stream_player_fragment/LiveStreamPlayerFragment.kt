package com.iso.multistream.ui.player.fragments.live_stream_player_fragment

import android.os.Bundle
import android.view.View
import androidx.lifecycle.observe
import com.iso.multistream.auth.platforms.TwitchPlatform
import com.iso.multistream.ui.player.fragments.PlayerFragment
import com.iso.multistream.utils.NumbersConverter
import com.iso.multistream.utils.data_binding.ImageLoader
import com.iso.player.R
import com.iso.player.chat.chat_factories.PlayerData
import com.iso.player.ui.MultiStreamPlayerLayout

class LiveStreamPlayerFragment : PlayerFragment<LiveStreamPlayerViewModel>(LiveStreamPlayerViewModel::class.java) {

    var viewCount: Int = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (view as MultiStreamPlayerLayout).apply {
            //steam sync worker is disabled because can update stream data live through chat input stream
//            viewModel.getLiveStreamPeriodicWorkLiveData().observe(viewLifecycleOwner) {
//                if (it?.state == WorkInfo.State.SUCCEEDED) {
//                    titleTextView.text = it.outputData.getString("title")
//                    viewersCount.text = NumbersConverter.getK(
//                        it.outputData.getInt("viewers_count", 0),
//                        requireContext()
//                    )
//                }
//            }
//            viewModel.startSyncPeriodicWork(channelId)
            viewModel.streamLiveData.observe(viewLifecycleOwner) {
                if(it != null) {
                    it.stream?.apply {
                        titleTextView.text = channel?.status
                        channelNameView.text = channel?.displayName
                        viewersCount.text = NumbersConverter.getK(viewers, requireContext())
                        categoryView.text = game
                        ImageLoader.loadImage(profileImageView, channel?.logo)
                    }
                }
            }
            viewModel.getStreamData(channelName)
        }
    }

    override fun initPlayer() {
        (view as MultiStreamPlayerLayout).apply {
            val playerType =
                PlayerData.TwitchChatType(
                    PlayerData.PlayerType.LIVE,
                    mainViewModel.getTwitchUser()?.name,
                    mainViewModel.getToken(TwitchPlatform::class.java),
                    channelName,
                    title,
                    displayName,
                    category,
                    imageUrl,
                    NumbersConverter.getK(viewCount, requireContext()),
                    null,
                    null
                )
            initializePlayer(playerType)
        }
    }

    override fun getArgs() {
        super.getArgs()
        viewCount = arguments?.getInt("viewer_count", 0)!!
    }

    override fun getPlayerLayout(): Int {
       return R.layout.player_layout
    }

    override fun getPlayerLayoutLand(): Int {
       return R.layout.player_layout_land
    }

    override fun observeUserData(): Boolean {
        return true
    }
}