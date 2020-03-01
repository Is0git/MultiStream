package com.android.multistream.ui.main.fragments.home_fragment.view_model

import androidx.lifecycle.MutableLiveData
import com.android.multistream.auth.PlatformManager
import com.android.multistream.auth.Platforms.TwitchPlatform
import com.android.multistream.di.main_activity.main_fragments.home_fragment.HomeFragmentScope
import com.android.multistream.di.qualifiers.TwitchQualifier
import com.android.multistream.network.twitch.TwitchService
import com.android.multistream.network.twitch.models.new_twitch_api.channels.DataItem
import com.android.multistream.network.twitch.models.v5.followed_streams.Followed
import retrofit2.Retrofit
import javax.inject.Inject

@HomeFragmentScope
class HomeFragmentRepository @Inject constructor(@TwitchQualifier val retrofit: Retrofit, val platformManager: PlatformManager) {
    val twitchService = retrofit.create(TwitchService::class.java)
    val topChannelsLiveData = MutableLiveData<MutableList<DataItem>?>()
    val followedStreamsLiveData = MutableLiveData<Followed>()

    suspend fun getTopChannels() {
        val twitchResult = twitchService.getChannels("0", 10, null)
        topChannelsLiveData.postValue(twitchResult.body()?.data)
    }


    suspend fun getFollowedLiveStreams(type: String) {
        val accessToken = platformManager.getAccessToken(TwitchPlatform::class.java)
        val featured = twitchService.getFollowedLiveStreams(
            "OAuth $accessToken",
             type
        )
        followedStreamsLiveData.postValue(featured.body())
    }

}