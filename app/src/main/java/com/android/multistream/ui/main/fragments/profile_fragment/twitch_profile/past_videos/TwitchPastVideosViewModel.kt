package com.android.multistream.ui.main.fragments.profile_fragment.twitch_profile.past_videos

import androidx.lifecycle.ViewModel
import com.android.multistream.di.main_activity.main_fragments.profile_fragments.twitch_profile_fragment.ProfileFragmentScope
import javax.inject.Inject

@ProfileFragmentScope
class TwitchPastVideosViewModel @Inject constructor(var repo: TwitchPastClipsRepository): ViewModel() {
    val pageLoader = repo.pageLoader
}