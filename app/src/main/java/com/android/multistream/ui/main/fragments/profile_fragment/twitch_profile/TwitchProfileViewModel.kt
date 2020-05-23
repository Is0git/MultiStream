package com.android.multistream.ui.main.fragments.profile_fragment.twitch_profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.multistream.di.main_activity.main_fragments.profile_fragments.twitch_profile_fragment.ProfileFragmentScope
import com.android.multistream.utils.ResponseHandler
import kotlinx.coroutines.launch
import javax.inject.Inject

@ProfileFragmentScope
class TwitchProfileViewModel @Inject constructor(var twitchProfileRepository: TwitchProfileRepository) : ViewModel() {

    val followingLiveData = twitchProfileRepository.followsLiveData

    fun getFollowUser(channelId: String) {
       viewModelScope.launch { twitchProfileRepository.getFollowUser(channelId) }
    }
}