package com.android.multistream.ui.main.fragments.profile_fragment.mixer_profile.vods

import android.app.Application
import com.android.multistream.di.main_activity.main_fragments.profile_fragments.twitch_profile_fragment.ProfileFragmentScope
import com.android.multistream.network.mixer.MixerService
import com.android.multistream.network.mixer.models.vods.MixerVods
import com.android.multistream.network.mixer.models.vods.VodsItem
import com.android.multistream.ui.main.fragments.browse_fragment.PagedLoaderRepository
import com.android.multistream.utils.ResponseHandler.execute
import javax.inject.Inject

@ProfileFragmentScope
class MixerVodsRepository @Inject constructor(var application: Application, var mixerService: MixerService) : PagedLoaderRepository<MixerVods>(application, 0, 10, false) {

    var id: String? = null
    var order: String? = null

    override suspend fun getInitial(page: Int, pageLimit: Int): List<MixerVods>? {
        return execute(application) {mixerService.getVods("channelId:eq:$id", pageLimit, page, order)}
    }

    override suspend fun getNext(page: Int, pageLimit: Int): List<MixerVods>? {
       return execute(application) {mixerService.getVods("channelId:eq:$id", pageLimit, page, order)}
    }
}