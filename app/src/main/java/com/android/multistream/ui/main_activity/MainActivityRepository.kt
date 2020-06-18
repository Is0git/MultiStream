package com.android.multistream.ui.main_activity

import android.app.Application
import com.android.multistream.auth.platform_manager.PlatformManager
import com.android.multistream.auth.platforms.Platform
import com.android.multistream.auth.platforms.TwitchPlatform
import com.android.multistream.di.main_activity.MainActivityScope
import com.android.multistream.network.mixer.MixerService
import com.android.multistream.network.twitch.TwitchService
import com.android.multistream.network.twitch.models.v5.current_user.CurrentUser
import com.android.multistream.network.twitch.models.v5.followed_streams.StreamsItem
import com.android.multistream.ui.main_activity.fragments.browse_fragment.PageOffSetLoaderRepository
import com.android.multistream.utils.ResponseHandler
import com.android.multistream.utils.ResponseHandler.execute
import com.android.multistream.utils.ResponseHandler.handleNetworkException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

@MainActivityScope
class MainActivityRepository @Inject constructor(
    var application: Application,
    var twitchService: TwitchService,
    var mixerService: MixerService,
    val platformManager: PlatformManager
) : PageOffSetLoaderRepository<StreamsItem>(application, 0, 10, false) {



    fun isValidated(clazz: Class<out Platform<*, *, *, *>>): Boolean {
        return platformManager.getPlatform(clazz).isValidated
    }

    fun getToken(clazz: Class<out Platform<*, *, *, *>>): String? {
        return platformManager.getAccessToken(clazz)
    }

    fun getTwitchUser(): CurrentUser? {
        return platformManager.getPlatform(TwitchPlatform::class.java).currentUser
    }

    fun saveAccessToken(
        platformClass: Class<out Platform<*, *, *, *>>,
        accessToken: String?,
        refreshToken: String?
    ) {
        platformManager.saveAccessTokenInPreference(platformClass, accessToken, refreshToken)
    }

    override suspend fun getInitial(pageOffSet: Int, pageLimit: Int): List<StreamsItem>? {
        return getStreams(pageOffSet, pageLimit)
    }

    override suspend fun getNext(pageOffSet: Int, pageLimit: Int): List<StreamsItem>? {
        return getStreams(pageOffSet, pageLimit)

    }

    private suspend fun getStreams(pageOffSet: Int, limit: Int): List<StreamsItem>? {
        return ResponseHandler.execute(application) {
            twitchService.getFollowedStreams(
                "OAuth ${platformManager.getAccessToken(
                    TwitchPlatform::class.java
                )}", "all", limit, pageOffSet
            )
        }?.streams
    }

    suspend fun followTwitchUser(channelId: String) {
        execute(application) {
            twitchService.followChannel(
                getTwitchUser()?._id?.toString(),
                channelId, "OAuth ${platformManager.getAccessToken(TwitchPlatform::class.java)}"
            )
        }
        withContext(Dispatchers.Main) {pageLoader.invalidate(true)}
    }

    suspend fun unFollowTwitchUser(channelId: String) {
        try {
            twitchService.unFollowChannel(
                getTwitchUser()?._id?.toString(),
                channelId,
                "OAuth ${platformManager.getAccessToken(TwitchPlatform::class.java)}"
            )
            withContext(Dispatchers.Main) {pageLoader.invalidate(true)}
        } catch (ex: Exception) {
            handleNetworkException(ex, application)
        }
    }
}