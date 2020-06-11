package com.android.multistream.ui.main_activity.fragments.profile_fragment.twitch_profile.past_videos

import android.app.Application
import com.android.multistream.auth.platform_manager.PlatformManager
import com.android.multistream.auth.platforms.TwitchPlatform
import com.android.multistream.di.main_activity.main_fragments.profile_fragments.twitch_profile_fragment.ProfileFragmentScope
import com.android.multistream.network.twitch.TwitchService
import com.android.multistream.network.twitch.models.new_twitch_api.clips.DataItem
import com.android.multistream.ui.main_activity.fragments.browse_fragment.PageOffSetLoaderRepository
import com.android.multistream.utils.ResponseHandler
import javax.inject.Inject

@ProfileFragmentScope
class TwitchPastClipsRepository @Inject constructor(var application: Application, var twitchService: TwitchService, var platformManager: PlatformManager) : PageOffSetLoaderRepository<DataItem>(application, 0, 10, false) {

    var userId: String? = "48878319"
    var after: String? = null

    override suspend fun getInitial(pageOffSet: Int, pageLimit: Int): List<DataItem>? {
        return getData(pageLimit, after)
    }

    override suspend fun getNext(pageOffSet: Int, pageLimit: Int): List<DataItem>? {
        return getData(pageLimit, after)
    }

    private suspend fun getData(limit: Int, after: String?): List<DataItem>? {
        val token = platformManager.getAccessToken(TwitchPlatform::class.java)
        val result = ResponseHandler.execute(application) {
            twitchService.getClips(
                after,
                limit,
                userId,
                "Bearer $token"
            )
        }
        this.after = result?.pagination?.cursor
        return result?.data
    }
}