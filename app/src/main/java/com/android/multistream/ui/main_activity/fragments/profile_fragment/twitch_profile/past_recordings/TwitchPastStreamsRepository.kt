package com.android.multistream.ui.main_activity.fragments.profile_fragment.twitch_profile.past_recordings

import android.app.Application
import com.android.multistream.R
import com.android.multistream.auth.platform_manager.PlatformManager
import com.android.multistream.auth.platforms.TwitchPlatform
import com.android.multistream.di.main_activity.main_fragments.profile_fragments.twitch_profile_fragment.ProfileFragmentScope
import com.android.multistream.network.twitch.TwitchService
import com.android.multistream.network.twitch.models.new_twitch_api.videos.DataItem
import com.android.multistream.ui.main_activity.fragments.browse_fragment.PageOffSetLoaderRepository
import com.android.multistream.utils.ResponseHandler.execute
import javax.inject.Inject

@ProfileFragmentScope
class TwitchPastStreamsRepository @Inject constructor(
    var application: Application,
    var twitchService: TwitchService,
    var platformManager: PlatformManager
) : PageOffSetLoaderRepository<DataItem>(
    application,
    pageStartOffSet = 0,
    pageLimit = 10,
    isAutoLoad = false
) {

    var userId: String? = "48878319"
    var after: String? = null
    var period: String? = "all"
    var sort: String? = "time"
    var type: String? = "all"
    val sortMap = mapOf(
        application.getString(R.string.time) to "time",
        application.getString(R.string.trending) to "trending",
        application.getString(R.string.views_sort) to "views"
    )
    val periodMap = mapOf(
        application.getString(R.string.all) to "all",
        application.getString(R.string.day) to "day",
        application.getString(R.string.week) to "week",
        application.getString(R.string.month) to "month"
    )
    val categoriesMap = mapOf(
        application.getString(R.string.all) to "all",
        application.getString(R.string.upload) to "upload",
        application.getString(R.string.past_stream) to "archive",
        application.getString(R.string.highlight) to "highlight"
    )

    override suspend fun getInitial(page: Int, pageLimit: Int): List<DataItem>? {
        return getData(pageLimit, after)
    }

    override suspend fun getNext(page: Int, pageLimit: Int): List<DataItem>? {
        return getData(pageLimit, after)
    }

    private suspend fun getData(limit: Int, after: String?): List<DataItem>? {
        val token = platformManager.getAccessToken(TwitchPlatform::class.java)
        val result = execute(application) {
            twitchService.getVideos(
                after,
                limit,
                userId,
                period,
                sort,
                type,
                "Bearer $token"
            )
        }
        this.after = result?.pagination?.cursor
        return result?.data
    }
}