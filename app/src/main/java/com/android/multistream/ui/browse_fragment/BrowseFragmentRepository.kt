package com.android.multistream.ui.browse_fragment

import android.app.Application
import android.widget.Toast
import com.android.multistream.di.MainActivity.browse_fragment.BrowseFragmentScope
import com.android.multistream.network.mixer.MixerService
import com.android.multistream.network.twitch.TwitchService
import com.android.multistream.network.twitch.models.Data
import com.android.multistream.util.pagination.PagedOffsetListener
import com.android.multistream.util.pagination.PagedOffsetLoader
import kotlinx.coroutines.*
import java.io.IOException
import javax.inject.Inject


@BrowseFragmentScope
class BrowseFragmentRepository @Inject constructor(
    val mixerService: MixerService,
    val application: Application,
    val service: TwitchService
) {

    var job: Job? = null

    val pageLimit = 20

    var pageOffSet = 0

    val listener = object : PagedOffsetListener<Data> {
        override fun loadInitial(pagination: PagedOffsetLoader<Data>) {
            job = CoroutineScope(Dispatchers.IO).launch {
                try {
                    val twitchResult = getTwitchTopGamesAsync(pageOffSet)
                    val mixerResult = getMixerTopGamesAsync(pagination.pageCount)
                    if (mixerResult.await().body() != null) twitchResult.await().body()?.addAll(
                        mixerResult.await().body()!!
                    )
                    when {
                        twitchResult.await().isSuccessful -> pagination.loadInit(
                            twitchResult.await().body()
                        ).also { pageOffSet += 20 }
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
                    if (mixerResult.await().body() != null) twitchResult.await().body()?.addAll(
                        mixerResult.await().body()!!
                    )
                    when {
                        twitchResult.await().isSuccessful -> pagination.loadNext(
                            twitchResult.await().body()
                        ).also { pageOffSet += 20 }

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

    suspend fun getTwitchTopGamesAsync(page: Int) = coroutineScope {
        async { service.getTopGamesV5(page, pageLimit) }
    }

}