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
import com.android.multistream.util.pagination.PagedOffsetListener
import com.android.multistream.util.pagination.PagedOffsetLoader
import kotlinx.coroutines.*
import retrofit2.Retrofit
import java.io.IOException
import javax.inject.Inject


@BrowseFragmentScope
class BrowseFragmentRepository @Inject constructor(@TwitchRetrofitQualifier val twitchRetrofit: Retrofit,
    val application: Application,
    @MixerRetrofitQualifier val mixerRetrofit: Retrofit
) {
    val service = twitchRetrofit.create(TwitchService::class.java)
    val mixerService = mixerRetrofit.create(MixerService::class.java)

    var job: Job? = null

    val pageLimit = 20

    var pageOffSet = 0

    val listener = object : PagedOffsetListener<Data> {
        override fun loadInitial(pagination: PagedOffsetLoader<Data>) {
            job = CoroutineScope(Dispatchers.IO).launch {
                try {
                    val twitchResult = getTwitchTopGamesAsync(pageOffSet)
                    val mixerResult = getMixerTopGamesAsync(pagination.pageCount)
                    if (mixerResult.await().body() != null)  twitchResult.await().body()?.addAll(mixerResult.await().body()!!)
                    when {
                        twitchResult.await().isSuccessful -> pagination.loadInit(
                            twitchResult.await().body()
                        ).also { pageOffSet+=20 }
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

        override fun loadNext(pagination: PagedOffsetLoader<Data>) {
            job = CoroutineScope(Dispatchers.IO).launch {
                try {
                    val twitchResult = getTwitchTopGamesAsync(pageOffSet)
                    val mixerResult = getMixerTopGamesAsync(pagination.pageCount)
                    if (mixerResult.await().body() != null) twitchResult.await().body()?.addAll(mixerResult.await().body()!!)
                    when {
                        twitchResult.await().isSuccessful -> pagination.loadNext(
                            twitchResult.await().body()
                        ).also {  pageOffSet+=20 }

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

    suspend fun getMixerTopGamesAsync(page: Int) = coroutineScope {
        async {
            mixerService.getMixerTopGames(
                "viewersCurrent:gt:0",
                "viewersCurrent:DESC",
                pageLimit,
                page
            )
        }

    }

    suspend fun getTwitchTopGamesAsync(page: Int) =  coroutineScope {
        async { service.getTopGamesV5(page, pageLimit) }
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