package com.iso.multistream.ui.main_activity.fragments.browse_fragment.view_pager_fragments.mixer_fragment

import android.app.Application
import com.iso.multistream.di.main_activity.main_fragments.browse_fragment.view_pager_fragments.mixer_fragment.MixerGamesBrowseFragmentScope
import com.iso.multistream.network.mixer.MixerService
import com.iso.multistream.network.mixer.models.top_games.MixerTopGames
import com.iso.multistream.ui.main_activity.fragments.browse_fragment.PageOffSetLoaderRepository
import com.iso.multistream.utils.ResponseHandler.execute
import javax.inject.Inject

@MixerGamesBrowseFragmentScope
class MixerGamesBrowseRepository @Inject constructor(
    private val mixerService: MixerService,
    val application: Application
) : PageOffSetLoaderRepository<MixerTopGames>(application, 0, 10, false) {

    override suspend fun getInitial(pageOffSet: Int, pageLimit: Int): List<MixerTopGames>? {
        return execute(application) {
            mixerService.getMixerTopGamesFull(
                "viewersCurrent:DESC",
                pageLimit,
                pageOffSet
            )
        }
    }

    override suspend fun getNext(pageOffSet: Int, pageLimit: Int): List<MixerTopGames>? {
        return execute(application) {
            mixerService.getMixerTopGamesFull(
                "viewersCurrent:DESC",
                pageLimit,
                pageOffSet
            )
        }
    }
}