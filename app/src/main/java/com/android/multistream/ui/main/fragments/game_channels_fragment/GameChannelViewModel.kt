package com.android.multistream.ui.main.fragments.game_channels_fragment

import androidx.lifecycle.ViewModel
import com.android.multistream.di.main_activity.main_fragments.game_channels_fragment.GameChannelsFragmentScope
import javax.inject.Inject

@GameChannelsFragmentScope
class GameChannelViewModel @Inject constructor(val repo: GameChannelsRepository) : ViewModel()