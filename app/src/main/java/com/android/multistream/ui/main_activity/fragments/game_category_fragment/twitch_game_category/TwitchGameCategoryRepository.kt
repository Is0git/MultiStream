package com.android.multistream.ui.main_activity.fragments.game_category_fragment.twitch_game_category

import android.app.Application
import com.android.multistream.auth.platform_manager.PlatformManager
import com.android.multistream.auth.platforms.TwitchPlatform
import com.android.multistream.di.main_activity.main_fragments.game_category_fragment.twitch_game_category_fragment.TwitchGameCategoryScope
import com.android.multistream.network.twitch.TwitchService
import com.android.multistream.network.twitch.models.new_twitch_api.channels.DataItem
import com.android.multistream.ui.main_activity.fragments.browse_fragment.PageOffSetLoaderRepository
import com.android.multistream.utils.ResponseHandler.execute
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

@TwitchGameCategoryScope
class TwitchGameCategoryRepository @Inject constructor(
    var application: Application,
    var twitchService: TwitchService,
    var platformManager: PlatformManager
) :
    PageOffSetLoaderRepository<DataItem>(application, 0, 10, false) {

    var gameId: Int = 32982
    set(value) {
        field = value
        pageLoader.loadInit()
    }

    var pageCursor: String? = null

    override suspend fun getInitial(pageOffSet: Int, pageLimit: Int): List<DataItem>? {
        val channelsResult = execute(application) {
            twitchService.getChannels(
                null,
                pageLimit,
                gameId,
                "Bearer ${platformManager.getAccessToken(TwitchPlatform::class.java)}"
            )
        }
        pageCursor = channelsResult?.pagination?.cursor
        if (channelsResult?.data == null) throw CancellationException("null")
        mapChannelWithUser(channelsResult.data)
        return channelsResult.data
    }

    override suspend fun getNext(pageOffSet: Int, pageLimit: Int): List<DataItem>? {
        val channelsResult = execute(application) {
            twitchService.getChannels(
                pageCursor,
                pageLimit + 1,
                gameId,
                "Bearer ${platformManager.getAccessToken(TwitchPlatform::class.java)}"
            )
        }
        pageCursor = channelsResult?.pagination?.cursor
        if (channelsResult?.data == null) throw CancellationException("null")
        mapChannelWithUser(channelsResult.data)
        return channelsResult.data
    }

    private suspend fun mapChannelWithUser(dataItem: List<DataItem>) {
        coroutineScope {
            dataItem.forEach {
                launch(Dispatchers.IO) {
                    it.user = execute(application) { twitchService.getUser(it.user_id?.toInt()) }
                }
            }
        }
    }
}