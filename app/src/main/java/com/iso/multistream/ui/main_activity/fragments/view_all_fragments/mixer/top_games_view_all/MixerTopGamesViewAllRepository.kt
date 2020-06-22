package com.iso.multistream.ui.main_activity.fragments.view_all_fragments.mixer.top_games_view_all

import android.app.Application
import com.iso.multistream.di.main_activity.main_fragments.view_all_fragments.ViewAllFragmentScope
import com.iso.multistream.network.mixer.MixerService
import com.iso.multistream.network.mixer.models.top_games.MixerTopGames
import com.iso.multistream.ui.main_activity.fragments.browse_fragment.PagedLoaderRepository
import com.iso.multistream.utils.ResponseHandler
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