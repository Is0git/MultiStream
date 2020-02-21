package com.android.multistream.ui.main.fragments.home_fragment.view_model

import androidx.lifecycle.MutableLiveData
import com.android.multistream.di.main_activity.main_fragments.home_fragment.HomeFragmentScope
import com.android.multistream.di.qualifiers.TwitchQualifier
import com.android.multistream.network.twitch.TwitchService
import com.android.multistream.network.twitch.models.channels.DataItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import retrofit2.Retrofit
import javax.inject.Inject
@HomeFragmentScope
class HomeFragmentRepository @Inject constructor(@TwitchQualifier val retrofit: Retrofit) {
    val service = retrofit.create(TwitchService::class.java)
    val topChannelsLiveData = MutableLiveData<MutableList<DataItem>?>()
    suspend fun getTopChannels() = coroutineScope {
        var twitchResult = async(Dispatchers.IO) {service.getChannels("0", 10, null)}
        topChannelsLiveData.postValue(twitchResult.await().body()?.data)


    }

}