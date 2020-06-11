package com.android.multistream.ui.player.fragments

import android.app.Application
import androidx.lifecycle.*
import androidx.work.*
import com.android.multistream.auth.platform_manager.PlatformManager
import com.android.multistream.auth.platforms.TwitchPlatform
import com.android.multistream.di.main_activity.player_fragment.PlayerFragmentScope
import com.android.multistream.network.twitch.TwitchService
import com.android.multistream.network.twitch.models.v5.current_user.CurrentUser
import com.android.multistream.network.twitch.models.v5.follow.FollowUser
import com.android.multistream.network.twitch.models.v5.single_stream.SingleStream
import com.android.multistream.utils.ResponseHandler
import com.android.multistream.utils.ResponseHandler.execute
import com.android.multistream.utils.WorkFactory
import com.android.multistream.workers.SyncLiveStreamWorker
import java.util.concurrent.TimeUnit
import javax.inject.Inject

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