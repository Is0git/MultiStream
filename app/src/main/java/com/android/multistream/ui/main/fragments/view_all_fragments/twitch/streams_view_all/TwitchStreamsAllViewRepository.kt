package com.android.multistream.ui.main.fragments.view_all_fragments.twitch.streams_view_all

import android.app.Application
import com.android.multistream.auth.platform_manager.PlatformManager
import com.android.multistream.auth.platforms.TwitchPlatform
import com.android.multistream.di.main_activity.main_fragments.view_all_fragments.ViewAllFragmentScope
import com.android.multistream.network.twitch.TwitchService
import com.android.multistream.network.twitch.models.v5.followed_streams.StreamsItem
import com.android.multistream.ui.main.fragments.browse_fragment.PageOffSetLoaderRepository
import com.android.multistream.utils.ResponseHandler.execute
import java.util.stream.Stream
import javax.inject.Inject

@ViewAllFragmentScope
class TwitchStreamsAllViewRepository @Inject constructor(
    var application: Application,
    var twitchService: TwitchService,
    var platformManager: PlatformManager
) : PageOffSetLoaderRepository<StreamsItem>(application, 0, 10, false) {
    override suspend fun getInitial(pageOffSet: Int, pageLimit: Int): List<StreamsItem>? {
        return getStreams(pageOffSet, pageLimit)
    }

    override suspend fun getNext(pageOffSet: Int, pageLimit: Int): List<StreamsItem>? {
        return getStreams(pageOffSet, pageLimit)

    }

    private suspend fun getStreams(pageOffSet: Int, limit: Int): List<StreamsItem>? {
        return execute(application) {
            twitchService.getFollowedStreams(
                "OAuth ${platformManager.getAccessToken(
                    TwitchPlatform::class.java
                )}", "live", limit, pageOffSet
            )
        }?.streams
    }
}