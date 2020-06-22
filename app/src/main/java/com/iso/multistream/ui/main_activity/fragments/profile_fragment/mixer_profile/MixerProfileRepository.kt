package com.iso.multistream.ui.main_activity.fragments.profile_fragment.mixer_profile

import android.app.Application
import com.iso.multistream.di.main_activity.main_fragments.profile_fragments.twitch_profile_fragment.ProfileFragmentScope
import com.iso.multistream.network.mixer.MixerService
import javax.inject.Inject

@ProfileFragmentScope
class MixerProfileRepository @Inject constructor(var mixerService: MixerService, var application: Application)