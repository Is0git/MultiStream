package com.android.multistream.ui.fragments.browse_fragment.view_pager_fragments.twitch_fragment

import android.app.Application
import android.widget.Toast
import com.android.multistream.di.MainActivity.browse_fragment.view_pager_fragments.twitch_fragment.TwitchFragmentGamesScope
import com.android.multistream.network.twitch.TwitchService
import com.android.multistream.network.twitch.models.v5.TopItem
import com.android.multistream.utils.pagination.PagedOffSetListener
import com.android.multistream.utils.pagination.PagedOffsetLoader
import kotlinx.coroutines.*
import java.io.IOException
import javax.inject.Inject

@TwitchFragmentGamesScope
class TwitchFragmentRepository @Inject constructor(val twitchService: TwitchService, val application: Application) : PagedOffSetListener<TopItem> {


    val pageLimit = 30
    var pageOffSet = 0
    var loadJob: Job? = null
    val pagedOffSetLoader = PagedOffsetLoader<TopItem>(this, pageLimit)

        override fun loadInitial(pagination: PagedOffsetLoader<TopItem>) {
            loadJob = CoroutineScope(Dispatchers.IO).launch {
                try {
                    val mixerResult =
                        getTwitchTopGamesV5(pagination.pageLimit, pagination.pageOffset)

                    when {
                        mixerResult.await().isSuccessful -> pagination.loadInit(mixerResult.await().body()?.top)
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


        override fun loadNext(pagination: PagedOffsetLoader<TopItem>) {
            loadJob = CoroutineScope(Dispatchers.IO).launch {
                try {
                    val mixerResult = getTwitchTopGamesV5(pagination.pageLimit, pagination.pageOffset)

                    when {
                        mixerResult.await().isSuccessful -> pagination.loadInit(mixerResult.await().body()?.top)
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


    suspend fun getTwitchTopGamesV5(limit: Int, offSet: Int) = coroutineScope {
        async { twitchService.getTopGamesV5Full(offSet, limit) }
    }

}