package com.android.multistream.ui.main_activity.fragments.profile_fragment.twitch_profile

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.android.multistream.auth.platform_manager.PlatformManager
import com.android.multistream.auth.platforms.TwitchPlatform
import com.android.multistream.di.main_activity.main_fragments.profile_fragments.twitch_profile_fragment.ProfileFragmentScope
import com.android.multistream.network.twitch.TwitchService
import com.android.multistream.network.twitch.models.v5.current_user.CurrentUser
import com.android.multistream.network.twitch.models.v5.follow.FollowUser
import com.android.multistream.utils.ResponseHandler.execute
import javax.inject.Inject

@ProfileFragmentScope
class TwitchProfileRepository @Inject constructor(var application: Application, var twitchService: TwitchService, var platformManager: PlatformManager){

    val followsLiveData: MutableLiveData<FollowUser?> = MutableLiveData()

    suspend fun getFollowUser(channelId: String) {
        execute(application) {twitchService.checkIfFollows(getTwitchUser()?._id.toString(), channelId)}.also { followsLiveData.postValue(it) }
    }

    private fun getTwitchUser(): CurrentUser? {
        return platformManager.getPlatform(TwitchPlatform::class.java).currentUser
    }

}