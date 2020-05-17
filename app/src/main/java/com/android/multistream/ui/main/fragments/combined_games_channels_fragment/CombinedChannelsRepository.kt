package com.android.multistream.ui.main.fragments.combined_games_channels_fragment

import com.android.multistream.di.main_activity.main_fragments.combined_channels_fragment.CombinedChannelsScope
import com.android.multistream.network.mixer.MixerService
import com.android.multistream.network.twitch.TwitchService
import javax.inject.Inject

@CombinedChannelsScope
class CombinedChannelsRepository @Inject constructor(
    val twitchService: TwitchService,
    val mixerService: MixerService
)