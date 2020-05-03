package com.android.multistream.ui.main.fragments.browse_fragment.view_pager_fragments.twitch_fragment

import android.app.Application
import android.widget.Toast
import com.android.multistream.di.main_activity.main_fragments.browse_fragment.view_pager_fragments.twitch_fragment.TwitchFragmentGamesScope
import com.android.multistream.network.twitch.TwitchService
import com.android.multistream.network.twitch.models.v5.top_games.TopItem
import com.android.multistream.pagination.PageLoader
import com.android.multistream.utils.ResponseHandler
import kotlinx.coroutines.*
import java.io.IOException
import javax.inject.Inject

@TwitchFragmentGamesScope
class TwitchFragmentRepository @Inject constructor(
    val twitchService: TwitchService,
    val application: Application
)  {


    val pageLoader = PageLoader(object : PageLoader.PagedOffSetListener<TopItem> {
        override suspend fun loadInitial(pageOffSet: Int, pageLimit: Int): List<TopItem>? {
            try {
                val result = twitchService.getTopGamesV5Full(pageOffSet, pageLimit)
                if (ResponseHandler.handleResponse(result, application)) return result.body()?.top
            } catch (e: IOException) {
                withContext(Dispatchers.Main) {
                    ResponseHandler.handleNetworkException(e, application)
                }
            }
            return null
        }

        override suspend fun loadNext(pageOffSet: Int, pageLimit: Int): List<TopItem>? {
            try {
                val result = twitchService.getTopGamesV5Full(pageOffSet, pageLimit)
                if (ResponseHandler.handleResponse(result, application)) return result.body()?.top
            } catch (e: IOException) {
                withContext(Dispatchers.Main) {
                    ResponseHandler.handleNetworkException(e, application)
                }
            }
            return null
        }

        override var pageOffSet: Int = 0

        override var pageLimit: Int = 20
    })

}