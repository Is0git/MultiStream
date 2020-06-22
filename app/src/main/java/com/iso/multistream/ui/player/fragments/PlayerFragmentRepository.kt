package com.iso.multistream.ui.player.fragments

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.iso.multistream.auth.platform_manager.PlatformManager
import com.iso.multistream.auth.platforms.TwitchPlatform
import com.iso.multistream.network.twitch.TwitchService
import com.iso.multistream.network.twitch.models.v5.current_user.CurrentUser
import com.iso.multistream.network.twitch.models.v5.follow.FollowUser
import com.iso.multistream.network.twitch.models.v5.single_stream.SingleStream
import com.iso.multistream.utils.ResponseHandler
import com.iso.multistream.utils.ResponseHandler.execute

abstract class PlayerFragmentRepository constructor(
    var app: Application,
    var twitchService: TwitchService,
    var platformManager: PlatformManager
) {
    val followsLiveData: MutableLiveData<FollowUser?> = MutableLiveData()
    var userLiveData = MutableLiveData<SingleStream?>()

    suspend fun getFollowUser(channelId: String) {
        execute(app) {
            twitchService.checkIfFollows(
                getTwitchUser()?._id.toString(),
                channelId
            )
        }.also { followsLiveData.postValue(it) }
    }

    private fun getTwitchUser(): CurrentUser? {
        return platformManager.getPlatform(TwitchPlatform::class.java).currentUser
    }


    suspend fun getStream(channelId: String): SingleStream? {
        return ResponseHandler.execute(app) { twitchService.getStream(channelId) }?.also { userLiveData.postValue(it) }
    }

}