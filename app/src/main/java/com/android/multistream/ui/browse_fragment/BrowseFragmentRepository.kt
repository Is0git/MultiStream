package com.android.multistream.ui.browse_fragment

import androidx.paging.toLiveData
import com.android.multistream.database.dao.TwitchDao
import com.android.multistream.database.entities.TopGames
import com.android.multistream.di.MainActivity.browse_fragment.BrowseFragmentScope
import com.android.multistream.di.TwitchRetrofitQualifier
import com.android.multistream.network.twitch.TwitchService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import javax.inject.Inject

@BrowseFragmentScope
class BrowseFragmentRepository @Inject constructor(val twitchDao: TwitchDao,@TwitchRetrofitQualifier val retrofit: Retrofit) {
    val service = retrofit.create(TwitchService::class.java)
    val topGames = twitchDao.getTopGames().toLiveData(5)


    suspend fun updateTopGames() {
        val result = service.getTopGames()
        when {
            result.isSuccessful -> result.body()?.apply {
                val res = withContext(Dispatchers.IO) {  data.map { TopGames(it.boxArtUrl, it.id!!, it.name) }}
                twitchDao.insertTopGames(res)
            }
        }
    }
}