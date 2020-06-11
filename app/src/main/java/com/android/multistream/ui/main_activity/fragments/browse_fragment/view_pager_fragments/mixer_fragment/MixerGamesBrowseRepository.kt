package com.android.multistream.ui.main_activity.fragments.browse_fragment.view_pager_fragments.mixer_fragment

import android.app.Application
import com.android.multistream.di.main_activity.main_fragments.browse_fragment.view_pager_fragments.mixer_fragment.MixerGamesBrowseFragmentScope
import com.android.multistream.network.mixer.MixerService
import com.android.multistream.network.mixer.models.top_games.MixerTopGames
import com.android.multistream.ui.main_activity.fragments.browse_fragment.PageOffSetLoaderRepository
import com.android.multistream.utils.ResponseHandler.execute
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