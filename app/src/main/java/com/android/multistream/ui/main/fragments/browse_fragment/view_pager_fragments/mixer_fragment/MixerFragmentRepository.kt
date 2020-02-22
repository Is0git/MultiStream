package com.android.multistream.ui.main.fragments.browse_fragment.view_pager_fragments.mixer_fragment

import android.app.Application
import android.widget.Toast
import com.android.multistream.di.main_activity.main_fragments.browse_fragment.view_pager_fragments.mixer_fragment.MixerFragmentGamesScope
import com.android.multistream.network.mixer.MixerService
import com.android.multistream.network.mixer.models.top_games.MixerTopGames
import com.android.multistream.pagination.PagedPositionListener
import com.android.multistream.pagination.PagedPositionLoader
import kotlinx.coroutines.*
import java.io.IOException
import javax.inject.Inject

@MixerFragmentGamesScope
class MixerFragmentRepository @Inject constructor(val mixerService: MixerService, val application: Application) : PagedPositionListener<MixerTopGames> {
    var loadJob: Job? = null
    val pageLimit = 20

    val mixerTopGamesPagination = PagedPositionLoader(this)


        override fun loadInitial(pagination: PagedPositionLoader<MixerTopGames>) {
            loadJob = CoroutineScope(Dispatchers.IO).launch {
                try {
                    val mixerResult = getMixerTopGamesAsync(pagination.pageCount)

                    when {
                        mixerResult.await().isSuccessful -> pagination.loadInit(mixerResult.await().body())
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

        override fun loadNext(pagination: PagedPositionLoader<MixerTopGames>) {
            loadJob = CoroutineScope(Dispatchers.IO).launch {
                try {
                    val mixerResult = getMixerTopGamesAsync(pagination.pageCount)

                    when {
                        mixerResult.await().isSuccessful -> pagination.loadNext(mixerResult.await().body())
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


    suspend fun getMixerTopGamesAsync(page: Int) = coroutineScope {
        async {
            mixerService.getMixerTopGamesFull(
                "viewersCurrent:DESC",
                pageLimit,
                page
            )
        }
    }
}