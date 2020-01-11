package com.android.multistream.ui.game_channels_fragment

import android.app.Application
import com.android.multistream.di.MainActivity.game_channels_fragment.GameChannelsFragmentScope
import com.android.multistream.network.mixer.MixerService
import com.android.multistream.network.twitch.TwitchService
import javax.inject.Inject

@GameChannelsFragmentScope
class GameChannelsRepository @Inject constructor(
    val mixerService: MixerService,
    val application: Application,
    val twitchService: TwitchService
) {


}