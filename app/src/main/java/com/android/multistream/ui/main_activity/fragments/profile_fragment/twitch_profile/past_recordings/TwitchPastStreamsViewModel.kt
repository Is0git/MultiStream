package com.android.multistream.ui.main_activity.fragments.profile_fragment.twitch_profile.past_recordings

import androidx.lifecycle.ViewModel
import com.android.multistream.di.main_activity.main_fragments.profile_fragments.twitch_profile_fragment.ProfileFragmentScope
import javax.inject.Inject

@ProfileFragmentScope
class TwitchPastStreamsViewModel @Inject constructor(var repo: TwitchPastStreamsRepository): ViewModel() {
    val pageLoader = repo.pageLoader
}