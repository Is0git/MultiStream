package com.android.multistream.ui.player.fragments.vod_player_fragment

import com.android.multistream.auth.platforms.TwitchPlatform
import com.android.multistream.ui.player.fragments.PlayerFragment
import com.android.multistream.utils.NumbersConverter
import com.android.player.R
import com.android.player.chat.chat_factories.PlayerData
import com.android.player.ui.MultiStreamPlayerLayout

class VodPlayerFragment : PlayerFragment<VodPlayerViewModel>(VodPlayerViewModel::class.java) {
    lateinit var vodId: String
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
                    null,
                    vodId,
                    null,
                    null
                )
            initializePlayer(playerType)
        }
    }

    override fun getArgs() {
        super.getArgs()
        vodId = arguments?.getString("vodId", "null")!!
    }

    override fun getPlayerLayout(): Int {
        return R.layout.vod_player_layout
    }

    override fun getPlayerLayoutLand(): Int {
        return R.layout.vod_player_layout_land
    }
}