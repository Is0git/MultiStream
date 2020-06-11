package com.android.multistream.ui.main_activity.fragments.view_all_fragments.mixer.top_games_view_all

import android.app.Application
import com.android.multistream.di.main_activity.main_fragments.view_all_fragments.ViewAllFragmentScope
import com.android.multistream.network.mixer.MixerService
import com.android.multistream.network.mixer.models.top_games.MixerTopGames
import com.android.multistream.network.twitch.TwitchService
import com.android.multistream.ui.main_activity.fragments.browse_fragment.PagedLoaderRepository
import com.android.multistream.utils.ResponseHandler
import javax.inject.Inject

@ViewAllFragmentScope
class MixerTopGamesViewAllRepository @Inject constructor(
    var application: Application,
    var mixerService: MixerService
) : PagedLoaderRepository<MixerTopGames>(application, 0, 10, false) {
    override suspend fun getInitial(page: Int, pageLimit: Int): List<MixerTopGames>? {
       return getMixerTopGames(page, pageLimit)
    }

    override suspend fun getNext(page: Int, pageLimit: Int): List<MixerTopGames>? {
        return getMixerTopGames(page, pageLimit)
    }

    suspend fun getMixerTopGames(page: Int, pageLimit: Int) : List<MixerTopGames>? {
        return ResponseHandler.execute(application) {
            mixerService.getMixerTopGamesFull(
                "viewersCurrent:DESC",
                pageLimit,
                page
            )
        }
    }
}