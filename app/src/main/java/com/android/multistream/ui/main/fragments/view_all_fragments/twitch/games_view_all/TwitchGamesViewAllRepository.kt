package com.android.multistream.ui.main.fragments.view_all_fragments.twitch.games_view_all

import android.app.Application
import com.android.multistream.di.main_activity.main_fragments.view_all_fragments.ViewAllFragmentScope
import com.android.multistream.network.twitch.TwitchService
import com.android.multistream.network.twitch.models.v5.top_games.TopItem
import com.android.multistream.ui.main.fragments.browse_fragment.PageOffSetLoaderRepository
import com.android.multistream.utils.ResponseHandler.execute
import javax.inject.Inject

@ViewAllFragmentScope
class TwitchGamesViewAllRepository @Inject constructor(var application: Application, var twitchService: TwitchService) : PageOffSetLoaderRepository<TopItem>(
    application, 0, 10, false
) {
    override suspend fun getInitial(pageOffSet: Int, pageLimit: Int): List<TopItem>? {
        return getGames(pageOffSet, pageLimit)
}

    override suspend fun getNext(pageOffSet: Int, pageLimit: Int): List<TopItem>? {
       return getGames(pageOffSet, pageLimit+1)
    }

    private suspend fun getGames(pageOffSet: Int, pageLimit: Int) : List<TopItem>? {
        return execute(application) {twitchService.getTopGamesV5Full(pageOffSet, pageLimit)}?.top
    }
}