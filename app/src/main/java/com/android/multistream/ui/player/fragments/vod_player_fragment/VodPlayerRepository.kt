package com.android.multistream.ui.player.fragments.vod_player_fragment

import android.app.Application
import com.android.multistream.auth.platform_manager.PlatformManager
import com.android.multistream.di.main_activity.player_fragment.PlayerFragmentScope
import com.android.multistream.network.twitch.TwitchService
import com.android.multistream.ui.player.fragments.PlayerFragmentRepository
import javax.inject.Inject

@PlayerFragmentScope
class VodPlayerRepository @Inject constructor(app: Application, twitchService: TwitchService, platformManager: PlatformManager) : PlayerFragmentRepository(app, twitchService, platformManager) {
}