package com.android.multistream.ui.intro.fragments

import androidx.lifecycle.ViewModel
import com.android.multistream.auth.PlatformManager
import com.android.multistream.auth.Platforms.TwitchPlatform
import com.android.multistream.di.main_activity.intro_fragments.scopes.IntroFragmentScope
import com.android.multistream.network.twitch.models.Token
import javax.inject.Inject

@IntroFragmentScope
class IntroViewModel @Inject constructor(val repo: IntroRepository) : ViewModel() {


}