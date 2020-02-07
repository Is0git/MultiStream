package com.android.multistream.ui.game_channels_fragment

import android.app.Application
import android.widget.Toast
import com.android.multistream.di.MainActivity.game_channels_fragment.GameChannelsFragmentScope
import com.android.multistream.network.mixer.MixerService
import com.android.multistream.network.mixer.models.channel.GameChannels
import com.android.multistream.network.twitch.TwitchService
import com.android.multistream.network.twitch.models.channels.DataItem
import com.android.multistream.utils.pagination.PagedKeyLoader
import com.android.multistream.utils.pagination.PagedPositionListener
import com.android.multistream.utils.pagination.PagedPositionLoader
import com.android.multistream.utils.pagination.PaginationListener
import kotlinx.coroutines.*
import java.io.IOException
import javax.inject.Inject

@GameChannelsFragmentScope
class GameChannelsRepository @Inject constructor(
    val mixerService: MixerService,
    val application: Application,
    val twitchService: TwitchService
) : PaginationListener<DataItem>, PagedPositionListener<GameChannels> {
    val pageLimit = 20
    var loadJob: Job? = null
    val keyPager: PagedKeyLoader<DataItem>? by lazy { PagedKeyLoader(this, pageLimit) }
    val pagedPositionLoader by lazy { PagedPositionLoader(this) }
    var gameId: String? = null


    override fun loadInitial(pagination: PagedKeyLoader<DataItem>) {
        loadJob = CoroutineScope(Dispatchers.Default).launch {
            try {
                val twitchResult = getTwitchGameChannels(gameId!!, pagination.nextKey, pageLimit)

                when {
                    twitchResult.isSuccessful -> pagination.loadInit(
                        twitchResult.body()?.pagination?.cursor,
                        twitchResult.body()?.data
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

    override fun loadNext(pagination: PagedKeyLoader<DataItem>, nextKey: String?) {
        loadJob = CoroutineScope(Dispatchers.Default).launch {
            try {
                val twitchResult = getTwitchGameChannels(gameId!!, pagination.nextKey, pageLimit)

                when {
                    twitchResult.isSuccessful -> pagination.loadInit(
                        twitchResult.body()?.pagination?.cursor,
                        twitchResult.body()?.data
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

    private suspend fun getTwitchGameChannels(id: String, after: String?, first: Int) =
        coroutineScope {
            withContext(Dispatchers.IO) { twitchService.getChannels(after, first, id) }
        }


    private suspend fun getMixerGameChannels(gameId: String?, page: Int) = withContext(Dispatchers.IO) {mixerService.getGameChannels(gameId, page, pageLimit, "viewersCurrent:DESC")}


    override fun loadInitial(pagination: PagedPositionLoader<GameChannels>) {
        loadJob = CoroutineScope(Dispatchers.Default).launch {
            try {
                val mixerResult = getMixerGameChannels(gameId!!, pagination.pageCount)

                when {
                    mixerResult.isSuccessful -> pagination.loadInit(mixerResult.body())
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

    override fun loadNext(pagination: PagedPositionLoader<GameChannels>) {
        loadJob = CoroutineScope(Dispatchers.Default).launch {
            try {
                val mixerResult = getMixerGameChannels(gameId!!, pagination.pageCount)

                when {
                    mixerResult.isSuccessful -> pagination.loadNext(mixerResult.body())
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