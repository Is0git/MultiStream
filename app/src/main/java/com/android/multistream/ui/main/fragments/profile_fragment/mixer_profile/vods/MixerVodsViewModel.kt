package com.android.multistream.ui.main.fragments.profile_fragment.mixer_profile.vods

import androidx.lifecycle.ViewModel
import com.android.multistream.di.main_activity.main_fragments.profile_fragments.twitch_profile_fragment.ProfileFragmentScope
import javax.inject.Inject

@ProfileFragmentScope
class MixerVodsViewModel @Inject constructor(var repo: MixerVodsRepository) : ViewModel() {

    val pageLoader = repo.pageLoader
}