package com.iso.multistream.ui.player.fragments.vod_player_fragment

import android.app.Application
import com.iso.multistream.auth.platform_manager.PlatformManager
import com.iso.multistream.di.main_activity.player_fragment.PlayerFragmentScope
import com.iso.multistream.network.twitch.TwitchService
import com.iso.multistream.ui.player.fragments.PlayerFragmentRepository
import javax.inject.Inject

@PlayerFragmentScope
class VodPlayerRepository @Inject constructor(app: Application, twitchService: TwitchService, platformManager: PlatformManager) : PlayerFragmentRepository(app, twitchService, platformManager)