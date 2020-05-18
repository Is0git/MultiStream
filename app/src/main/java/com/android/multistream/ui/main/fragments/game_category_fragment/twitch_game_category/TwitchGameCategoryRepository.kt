package com.android.multistream.ui.main.fragments.game_category_fragment.twitch_game_category

import android.app.Application
import android.util.Log
import com.android.multistream.auth.platform_manager.PlatformManager
import com.android.multistream.auth.platforms.TwitchPlatform
import com.android.multistream.di.main_activity.main_fragments.game_category_fragment.twitch_game_category_fragment.TwitchGameCategoryScope
import com.android.multistream.network.twitch.TwitchService
import com.android.multistream.network.twitch.models.new_twitch_api.channels.DataItem
import com.android.multistream.ui.main.fragments.browse_fragment.PageLoaderRepository
import com.android.multistream.utils.ResponseHandler.execute
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.withContext
import javax.inject.Inject

@TwitchGameCategoryScope
class TwitchGameCategoryRepository @Inject constructor(
    var application: Application,
    var twitchService: TwitchService,
    var platformManager: PlatformManager
) :
    PageLoaderRepository<DataItem>(application, 0, 10) {

    var gameId: Int = 32982

    override suspend fun getInitial(pageOffSet: Int, pageLimit: Int): List<DataItem>? {
        val channelsResult = execute(application) {
            twitchService.getChannels(
                null,
                pageLimit,
                gameId,
                "Bearer ${platformManager.getAccessToken(TwitchPlatform::class.java)}"
            )
        } ?: return null
        flow<DataItem> { channelsResult.data }.onEach {  Log.d("HOMETAG", "ThreadOnEach: ${Thread.currentThread().name}")  }.buffer().map {
            Log.d("HOMETAG", "ThreadOnMap: ${Thread.currentThread().name}")
            mapChannelWithUser(it) }.flowOn(Dispatchers.Main)
            .collect() {
                Log.d("HOMETAG", "ThreadonCollect: ${Thread.currentThread().name}")
            }
        return channelsResult?.data
    }

    override suspend fun getNext(pageOffSet: Int, pageLimit: Int): List<DataItem>? {
        val channelsResult = execute(application) {
            twitchService.getChannels(
                pageOffSet.toString(),
                pageLimit,
                gameId,
                "Bearer ${platformManager.getAccessToken(TwitchPlatform::class.java)}"
            )
        }
        withContext(Dispatchers.IO) {
            flow<DataItem> { channelsResult?.data }.map {
                Log.d("HOMETAG", "Thread: ${Thread.currentThread().name}")
                mapChannelWithUser(it) }.buffer(10)
                .collect() { Log.d("HOMETAG", "Thread: ${Thread.currentThread().name}")}
        }

        return channelsResult?.data
    }

    private suspend fun mapChannelWithUser(dataItem: DataItem): DataItem {
        dataItem.user = execute(application) { twitchService.getUser(dataItem.user_id?.toInt()) }
        return dataItem
    }
}