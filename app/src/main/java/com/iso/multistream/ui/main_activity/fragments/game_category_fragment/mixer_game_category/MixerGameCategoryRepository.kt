package com.iso.multistream.ui.main_activity.fragments.game_category_fragment.mixer_game_category

import android.app.Application
import com.iso.multistream.di.main_activity.main_fragments.game_category_fragment.mixer_game_category_fragment.MixerGameCategoryScope
import com.iso.multistream.network.mixer.MixerService
import com.iso.multistream.network.mixer.models.mixer_channels.MixerGameChannel
import com.iso.multistream.ui.main_activity.fragments.browse_fragment.PagedLoaderRepository
import com.iso.multistream.utils.ResponseHandler.execute
import javax.inject.Inject

@MixerGameCategoryScope
class MixerGameCategoryRepository @Inject constructor(
    var application: Application,
    var mixerService: MixerService
) :
    PagedLoaderRepository<MixerGameChannel>(application, 0, 10, false) {

    var id: Int = 70323
        set(value) {
            field = value
            pageLoader.loadInit()
        }

    override suspend fun getInitial(page: Int, pageLimit: Int): List<MixerGameChannel>? {
        return getData(page, pageLimit)
    }

    override suspend fun getNext(page: Int, pageLimit: Int): List<MixerGameChannel>? {
        return getData(page, pageLimit)
    }

    private suspend fun getData(page: Int, pageLimit: Int): List<MixerGameChannel>? {
        return execute(application) {
            mixerService.getChannels(
                "typeId:eq:$id",
                pageLimit,
                page,
                "online:desc,viewersCurrent:desc"
            )
        }
    }
}