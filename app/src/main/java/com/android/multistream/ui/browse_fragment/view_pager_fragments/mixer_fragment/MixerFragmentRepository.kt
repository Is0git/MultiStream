package com.android.multistream.ui.browse_fragment.view_pager_fragments.mixer_fragment

import android.app.Application
import android.widget.Toast
import com.android.multistream.di.MainActivity.browse_fragment.view_pager_fragments.mixer_fragment.MixerFragmentGamesScope
import com.android.multistream.network.mixer.MixerService
import com.android.multistream.network.mixer.models.top_games.MixerTopGames
import com.android.multistream.util.pagination.PagedOffsetListener
import com.android.multistream.util.pagination.PagedOffsetLoader
import kotlinx.coroutines.*
import java.io.IOException
import javax.inject.Inject

@MixerFragmentGamesScope
class MixerFragmentRepository @Inject constructor(val mixerService: MixerService, val application: Application) {
    var loadJob: Job? = null
    val pageLimit = 20

    var pageOffSet = 0

    val topGamesPaginationListener = object : PagedOffsetListener<MixerTopGames> {
        override fun loadInitial(pagination: PagedOffsetLoader<MixerTopGames>) {
            loadJob = CoroutineScope(Dispatchers.IO).launch {
                try {
                    val mixerResult = getMixerTopGamesAsync(pageOffSet)

                    when {
                        mixerResult.await().isSuccessful -> pagination.loadInit(mixerResult.await().body()).also { pageOffSet += pageLimit }
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

        override fun loadNext(pagination: PagedOffsetLoader<MixerTopGames>) {
            loadJob = CoroutineScope(Dispatchers.IO).launch {
                try {
                    val mixerResult = getMixerTopGamesAsync(pageOffSet)

                    when {
                        mixerResult.await().isSuccessful -> pagination.loadNext(mixerResult.await().body()).also { pageOffSet += pageLimit }
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
            mixerService.getMixerTopGamesFull(
                "viewersCurrent:gt:0",
                "viewersCurrent:DESC",
                pageLimit,
                page
            )
        }
    }
}