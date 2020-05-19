package com.android.multistream.ui.main.fragments.browse_fragment.view_pager_fragments.twitch_fragment

import android.app.Application
import com.android.multistream.di.main_activity.main_fragments.browse_fragment.view_pager_fragments.twitch_fragment.TwitchGamesBrowseFragmentScope
import com.android.multistream.network.twitch.TwitchService
import com.android.multistream.network.twitch.models.v5.top_games.TopItem
import com.android.multistream.ui.main.fragments.browse_fragment.PageOffSetLoaderRepository
import com.android.multistream.utils.ResponseHandler.execute
import javax.inject.Inject

@TwitchGamesBrowseFragmentScope
class TwitchGamesBrowseRepository @Inject constructor(
    private val twitchService: TwitchService,
    val application: Application
) : PageOffSetLoaderRepository<TopItem>(application, 0, 20, true) {

    override suspend fun getInitial(pageOffSet: Int, pageLimit: Int): List<TopItem>? {
        return execute(application) { twitchService.getTopGamesV5Full(pageOffSet, pageLimit) }?.top

    }

    override suspend fun getNext(pageOffSet: Int, pageLimit: Int): List<TopItem>? {
        return execute(application) { twitchService.getTopGamesV5Full(pageOffSet, pageLimit) }?.top
    }

}