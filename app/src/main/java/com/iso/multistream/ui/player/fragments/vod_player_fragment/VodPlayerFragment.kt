package com.iso.multistream.ui.player.fragments.vod_player_fragment

import com.iso.multistream.auth.platforms.TwitchPlatform
import com.iso.multistream.ui.player.fragments.PlayerFragment
import com.iso.multistream.utils.NumbersConverter
import com.iso.multistream.utils.data_binding.ImageLoader
import com.iso.player.R
import com.iso.player.chat.chat_factories.PlayerData
import com.iso.player.ui.MultiStreamPlayerLayout

class VodPlayerFragment : PlayerFragment<VodPlayerViewModel>(VodPlayerViewModel::class.java) {
    lateinit var vodId: String
    private var viewerCount: Int? = null
    override fun initPlayer() {
        (view as MultiStreamPlayerLayout).apply {
            val playerType =
                PlayerData.TwitchChatType(
                    PlayerData.PlayerType.VOD,
                    mainViewModel.getTwitchUser()?.name,
                    mainViewModel.getToken(TwitchPlatform::class.java),
                    channelName,
                    title,
                    displayName,
                    category,
                    imageUrl,
                    NumbersConverter.getK(viewerCount, context),
                    vodId,
                    null,
                    null
                )
            initializePlayer(playerType)
            ImageLoader.loadImageTwitchWithParams(profileImageView, imageUrl, 100, 100)
        }
    }

    override fun getArgs() {
        super.getArgs()
        vodId = arguments?.getString("vodId", "null")!!
        viewerCount = arguments?.getInt("view_count", 0)
    }

    override fun getPlayerLayout(): Int {
        return R.layout.vod_player_layout
    }

    override fun getPlayerLayoutLand(): Int {
        return R.layout.vod_player_layout_land
    }

    override fun observeUserData(): Boolean {
        return false
    }
}