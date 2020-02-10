package com.android.multistream.ui.fragments.browse_fragment.view_pager_fragments.top_fragment

import android.app.Application
import android.widget.Toast
import com.android.multistream.di.MainActivity.browse_fragment.view_pager_fragments.top_fragment.TopFragmentGamesScope
import com.android.multistream.network.mixer.MixerService
import com.android.multistream.network.twitch.TwitchService
import com.android.multistream.network.twitch.models.Data
import com.android.multistream.utils.pagination.PagedOffSetListener
import com.android.multistream.utils.pagination.PagedOffsetLoader
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*


import java.io.IOException
import javax.inject.Inject

@TopFragmentGamesScope
class TopFragmentRepository @Inject constructor(
    val application: Application,
    val twitchService: TwitchService,
    val mixerService: MixerService
) : PagedOffSetListener<Data>{

    var loadJob: Job? = null
    val pageLoader = PagedOffsetLoader(this, 20)

        override fun loadInitial(pagination: PagedOffsetLoader<Data>) {
            loadJob = CoroutineScope(Dispatchers.IO).launch {
                try {
                    val twitchResult =
                        getTwitchTopGamesAsync(pagination.pageOffset, pagination.pageLimit)
                    if (twitchResult.await().isSuccessful) {
                        topTwitchAndMixerGames(twitchResult.await().body()).map { getMixerGame(it) }
                            .collect()
                        pagination.loadInit(twitchResult.await().body())
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
            loadJob = CoroutineScope(Dispatchers.IO).launch {
                try {
                    val twitchResult = getTwitchTopGamesAsync(pagination.pageOffset, pagination.pageLimit)
                    if(twitchResult.await().isSuccessful && !twitchResult.await().body().isNullOrEmpty()) {
                        val result = topTwitchAndMixerGames(twitchResult.await().body()).map {getMixerGame(it)}.collect()
                        pagination.loadInit(twitchResult.await().body())
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


        suspend fun getTwitchTopGamesAsync(offSet:Int, limit: Int) = coroutineScope {
            async { twitchService.getTopGamesV5(offSet, limit) }

        }

        suspend fun getMixerGame(topItem: Data) : Data{
           val result = withContext(Dispatchers.IO) { mixerService.getMixerTopGame("name:eq:${topItem.name}", 1)}
            topItem.mixerTopGames = result.body()?.firstOrNull()
            return topItem

        }
        suspend fun topTwitchAndMixerGames(list: List<Data>?) : Flow<Data> = flow {list?.forEach { emit(it) }}

}