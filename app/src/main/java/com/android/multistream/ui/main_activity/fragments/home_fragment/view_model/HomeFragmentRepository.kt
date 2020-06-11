package com.android.multistream.ui.main_activity.fragments.home_fragment.view_model

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.android.multistream.auth.platform_manager.PlatformManager
import com.android.multistream.auth.platforms.TwitchPlatform
import com.android.multistream.di.main_activity.main_fragments.home_fragment.HomeFragmentScope
import com.android.multistream.network.mixer.MixerService
import com.android.multistream.network.mixer.models.mixer_channels.MixerGameChannel
import com.android.multistream.network.mixer.models.top_games.MixerTopGames
import com.android.multistream.network.twitch.TwitchService
import com.android.multistream.network.twitch.models.new_twitch_api.channels.DataItem
import com.android.multistream.network.twitch.models.new_twitch_api.top_games.TopGame
import com.android.multistream.network.twitch.models.v5.followed_streams.Followed
import com.android.multistream.network.twitch.models.v5.top_games.TopItem
import com.android.multistream.utils.ResponseHandler
import com.android.multistream.utils.ResponseHandler.execute
import javax.inject.Inject

@HomeFragmentScope
class HomeFragmentRepository @Inject constructor(
    var application: Application,
    val platformManager: PlatformManager,
    var twitchService: TwitchService,
    var mixerService: MixerService
) {

    val topChannelsLiveData = MutableLiveData<MutableList<DataItem>?>()
    val followedLiveStreamsLiveData = MutableLiveData<Followed?>()
    val followedStreamsLiveData = MutableLiveData<Followed?>()
    val topGamesLiveData = MutableLiveData<List<TopItem>>()
    val mixerTopGamesLiveData = MutableLiveData<List<MixerTopGames>>()
    val mixerTopChannelsLiveData = MutableLiveData<List<MixerGameChannel>>()

    suspend fun getTopChannels() {
        val twitchResult = execute(application) {
            twitchService.getChannels(
                first = 10,
                gameId = null,
                access_token = "Bearer ${platformManager.getAccessToken(TwitchPlatform::class.java)}"
            )
        }
        topChannelsLiveData.postValue(twitchResult?.data)
    }


    suspend fun getFollowedLiveStreams(type: String) {
        val accessToken = platformManager.getAccessToken(TwitchPlatform::class.java)
        val featured =
            execute(application) { twitchService.getFollowedStreams("OAuth $accessToken", type) }
        followedLiveStreamsLiveData.postValue(featured)
    }

    suspend fun getFollowedStreams(type: String) {
        val accessToken = platformManager.getAccessToken(TwitchPlatform::class.java)
        val featured =
            execute(application) { twitchService.getFollowedStreams("OAuth $accessToken", type) }
        followedStreamsLiveData.postValue(featured)
    }

    suspend fun getTopGames(limit: Int) {
        val topGames = execute(application) { twitchService.getTopGamesV5Full(0, limit) }?.top
        topGamesLiveData.postValue(topGames)
    }

    suspend fun getMixerTopGames(pageLimit: Int) {
        val mixerTopGames = execute(application) {
            mixerService.getMixerTopGamesFull(
                "viewersCurrent:DESC",
                pageLimit,
                0
            )
        }
        mixerTopGamesLiveData.postValue(mixerTopGames)
    }

    suspend fun getMixerTopChannels(pageLimit: Int) {
        val mixerTopChannels = execute(application) {
            mixerService.getChannels(
                null,
                pageLimit,
                0,
                "online:desc,viewersCurrent:desc"
            )
        }
        mixerTopChannelsLiveData.postValue(mixerTopChannels)
    }
}