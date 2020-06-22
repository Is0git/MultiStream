package com.iso.multistream.ui.main_activity.fragments.profile_fragment.mixer_profile

import androidx.lifecycle.ViewModel
import com.iso.multistream.di.main_activity.main_fragments.profile_fragments.twitch_profile_fragment.ProfileFragmentScope
import javax.inject.Inject

@ProfileFragmentScope
class MixerProfileViewModel @Inject constructor(var repo: MixerProfileRepository): ViewModel()