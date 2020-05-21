package com.android.multistream.ui.main.fragments.profile_fragment.mixer_profile.clips

import android.app.Application
import com.android.multistream.di.main_activity.main_fragments.profile_fragments.twitch_profile_fragment.ProfileFragmentScope
import com.android.multistream.network.mixer.MixerService
import javax.inject.Inject

@ProfileFragmentScope
class MixerClipsRepository @Inject constructor(var application: Application, var mixerService: MixerService)  {

    var id: Int? = null

}