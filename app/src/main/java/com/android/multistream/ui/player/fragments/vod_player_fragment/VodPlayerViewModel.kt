package com.android.multistream.ui.player.fragments.vod_player_fragment

import com.android.multistream.di.main_activity.player_fragment.PlayerFragmentScope
import com.android.multistream.ui.player.fragments.PlayerFragmentViewModel
import javax.inject.Inject

@PlayerFragmentScope
class VodPlayerViewModel @Inject constructor(repo: VodPlayerRepository) : PlayerFragmentViewModel<VodPlayerRepository>(repo) {
}