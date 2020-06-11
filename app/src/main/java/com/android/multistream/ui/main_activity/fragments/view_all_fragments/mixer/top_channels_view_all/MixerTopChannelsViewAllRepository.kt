package com.android.multistream.ui.main_activity.fragments.view_all_fragments.mixer.top_channels_view_all

import android.app.Application
import com.android.multistream.di.main_activity.main_fragments.view_all_fragments.ViewAllFragmentScope
import com.android.multistream.network.mixer.MixerService
import com.android.multistream.network.mixer.models.mixer_channels.MixerGameChannel
import com.android.multistream.ui.main_activity.fragments.browse_fragment.PagedLoaderRepository
import com.android.multistream.utils.ResponseHandler
import com.example.pagination.PageLoader
import javax.inject.Inject

@ViewAllFragmentScope
class MixerTopChannelsViewAllRepository @Inject constructor(var application: Application, var mixerService: MixerService) : PagedLoaderRepository<MixerGameChannel>(application, 0, 10, false){
    override suspend fun getInitial(page: Int, pageLimit: Int): List<MixerGameChannel>? {
        return getMixerTopChannels(page, pageLimit)
    }

    override suspend fun getNext(page: Int, pageLimit: Int): List<MixerGameChannel>? {
        return getMixerTopChannels(page, pageLimit)
    }

    private suspend fun getMixerTopChannels(page: Int, pageLimit: Int) : List<MixerGameChannel>? {
        return ResponseHandler.execute(application) {
            mixerService.getChannels(
                null,
                pageLimit,
                page,
                "online:desc,viewersCurrent:desc"
            )
        }
    }
}