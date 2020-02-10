package com.android.multistream.ui.fragments.home_fragment.view_model

import androidx.lifecycle.MutableLiveData
import com.android.multistream.di.MainActivity.home_fragment.HomeFragmentScope
import com.android.multistream.di.TwitchRetrofitQualifier
import com.android.multistream.network.twitch.TwitchService
import com.android.multistream.network.twitch.models.Data
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import retrofit2.Retrofit
import javax.inject.Inject
@HomeFragmentScope
class HomeFragmentRepository @Inject constructor(@TwitchRetrofitQualifier val retrofit: Retrofit) {
    val service = retrofit.create(TwitchService::class.java)
    val topChannelsLiveData = MutableLiveData<Data>()
    suspend fun getTopChannels() = coroutineScope {
        var twitchResult = async(Dispatchers.IO) {service.getChannels("0", 10, null)}


    }

}