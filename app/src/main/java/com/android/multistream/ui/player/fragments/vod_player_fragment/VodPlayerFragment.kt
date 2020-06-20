package com.android.multistream.ui.player.fragments.vod_player_fragment

import com.android.multistream.auth.platforms.TwitchPlatform
import com.android.multistream.ui.player.fragments.PlayerFragment
import com.android.multistream.utils.NumbersConverter
import com.android.multistream.utils.data_binding.ImageLoader
import com.android.player.R
import com.android.player.chat.chat_factories.PlayerData
import com.android.player.ui.MultiStreamPlayerLayout

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