package com.android.multistream.ui.home_fragment

import com.android.multistream.di.MainActivity.home_fragment.HomeFragmentScope
import com.android.multistream.di.TwitchRetrofitQualifier
import com.android.multistream.network.twitch.TwitchService
import retrofit2.Retrofit
import javax.inject.Inject
@HomeFragmentScope
class HomeFragmentRepository @Inject constructor(@TwitchRetrofitQualifier val retrofit: Retrofit) {
    val service = retrofit.create(TwitchService::class.java)


}