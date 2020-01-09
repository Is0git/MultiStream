package com.android.multistream.ui.browse_fragment

import android.app.Application
import android.widget.Toast
import androidx.paging.toLiveData
import com.android.multistream.database.dao.TwitchDao
import com.android.multistream.di.MainActivity.MixerRetrofitQualifier
import com.android.multistream.di.MainActivity.browse_fragment.BrowseFragmentScope
import com.android.multistream.di.TwitchRetrofitQualifier
import com.android.multistream.network.mixer.MixerService
import com.android.multistream.network.twitch.TwitchService
import com.android.multistream.network.twitch.models.Data
import com.android.multistream.util.pagination.Pagination
import com.android.multistream.util.pagination.PaginationListener
import kotlinx.coroutines.*
import retrofit2.Retrofit
import java.io.IOException
import javax.inject.Inject


@BrowseFragmentScope
class BrowseFragmentRepository @Inject constructor(
    val twitchDao: TwitchDao, @TwitchRetrofitQualifier val twitchRetrofit: Retrofit,
    val application: Application,
    @MixerRetrofitQualifier val mixerRetrofit: Retrofit
) {
    val service = twitchRetrofit.create(TwitchService::class.java)
    val mixerService = mixerRetrofit.create(MixerService::class.java)
    val topGames = twitchDao.getTopGames().toLiveData(5)
    var job: Job? = null



    val listener = object : PaginationListener<Data> {
        override fun loadInitial(pagination: Pagination.PagedKeyLoader<Data>) {
            job = CoroutineScope(Dispatchers.IO).launch {
                try {
                    val result = getTwitchTopGamesAsync(null)
                    val mixerResult = getMixerTopGamesAsync()
                    if (mixerResult.await().body() != null)  result.await().body()?.data?.addAll(mixerResult.await().body()!!)
                    when {
                        result.await().isSuccessful -> pagination.loadInit(
                            result.await().body()?.pagination?.cursor,
                            result.await().body()?.data
                        )
                    }
                } catch (e: IOException) {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(
                            application,
                            "Connect to internet",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            }
        }

        override fun loadNext(pagination: Pagination.PagedKeyLoader<Data>, nextKey: String?) {
            job = CoroutineScope(Dispatchers.IO).launch {
                try {
                    val result = service.getTopGames(nextKey)
                    when {
                        result.isSuccessful -> pagination.loadNext(
                            result.body()?.pagination?.cursor,
                            result.body()?.data
                        )
                    }
                } catch (e: IOException) {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(
                            application,
                            "Connect to internet",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            }
        }
    }

    suspend fun getMixerTopGamesAsync() = coroutineScope {
        async {
            mixerService.getMixerTopGames(
                "viewersCurrent:gt:0",
                "viewersCurrent:DESC",
                20
            )
        }
    }

    suspend fun getTwitchTopGamesAsync(after: String?) =  coroutineScope {
        async { service.getTopGames(null) }
    }
//    val paginationListener = object : PaginationListener {
//        override fun loadInitial() {

//        }
//
//        override fun loadNext() {
//            CoroutineScope(Dispatchers.IO).launch {
//                try {
//                    val result = service.getTopGames()
//                    when {
//                        result.isSuccessful -> result.body()
//                    }
//                } catch (e: IOException) {
//                    Toast.makeText(application, "Connect to internet", Toast.LENGTH_LONG).show()
//                }
//            }
//        }
//
//    }

//    suspend fun updateTopGames() {
//        try {
//            val result = service.getTopGames()
//            when {
//                result.isSuccessful -> result.body()?.apply {
//                    val res = withContext(Dispatchers.IO) {
//                        data.map {
//                            TopGames(
//                                it.boxArtUrl,
//                                it.id!!,
//                                it.name
//                            )
//                        }
//                    }
//                    twitchDao.deleteAllFromTopGames()
//                    twitchDao.insertTopGames(res)
//                }
//            }
//        } catch (e: IOException) {
//            Toast.makeText(application, "Connect to internet", Toast.LENGTH_LONG).show()
//        }
//
//
//    }
}