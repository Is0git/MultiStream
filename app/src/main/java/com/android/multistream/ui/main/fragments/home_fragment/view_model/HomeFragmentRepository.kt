package com.android.multistream.ui.main.fragments.home_fragment.view_model

import androidx.lifecycle.MutableLiveData
import com.android.multistream.auth.platform_manager.PlatformManager
import com.android.multistream.auth.platforms.TwitchPlatform
import com.android.multistream.di.main_activity.main_fragments.home_fragment.HomeFragmentScope
import com.android.multistream.di.qualifiers.TwitchQualifier
import com.android.multistream.network.twitch.TwitchService
import com.android.multistream.network.twitch.models.new_twitch_api.channels.DataItem
import com.android.multistream.network.twitch.models.new_twitch_api.top_games.TopGame
import com.android.multistream.network.twitch.models.v5.followed_streams.Followed
import retrofit2.Retrofit
import javax.inject.Inject

@HomeFragmentScope
class HomeFragmentRepository @Inject constructor(val platformManager: PlatformManager, var twitchService: TwitchService) {

    val topChannelsLiveData = MutableLiveData<MutableList<DataItem>?>()
    val followedLiveStreamsLiveData = MutableLiveData<Followed?>()
    val followedStreamsLiveData = MutableLiveData<Followed?>()
    val topGamesLiveData = MutableLiveData<MutableList<TopGame>>()

    suspend fun getTopChannels() {
        val twitchResult = twitchService.getChannels(first = 10, gameId = null, access_token = "Bearer ${platformManager.getAccessToken(TwitchPlatform::class.java)}")
        topChannelsLiveData.postValue(twitchResult.body()?.data)
    }


    suspend fun getFollowedLiveStreams(type: String) {
        val accessToken = platformManager.getAccessToken(TwitchPlatform::class.java)
        val featured = twitchService.getFollowedStreams(
            "OAuth $accessToken",
            type
        )
        followedLiveStreamsLiveData.postValue(featured.body())
    }

    suspend fun getFollowedStreams(type: String) {
        val accessToken = platformManager.getAccessToken(TwitchPlatform::class.java)
        val featured = twitchService.getFollowedStreams("OAuth $accessToken", type)
        followedStreamsLiveData.postValue(featured.body())
    }

    suspend fun getTopGames(limit: Int) {
        val topGames = twitchService.getTopGamesV5(0, limit)
        topGamesLiveData.postValue(topGames.body())
    }
}