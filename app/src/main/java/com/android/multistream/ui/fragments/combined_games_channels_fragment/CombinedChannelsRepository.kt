package com.android.multistream.ui.fragments.combined_games_channels_fragment

import com.android.multistream.di.MainActivity.combined_channels_fragment.CombinedChannelsScope
import com.android.multistream.network.mixer.MixerService
import com.android.multistream.network.twitch.TwitchService
import javax.inject.Inject
@CombinedChannelsScope
class CombinedChannelsRepository @Inject constructor(val twitchService: TwitchService, val mixerService: MixerService) {




}