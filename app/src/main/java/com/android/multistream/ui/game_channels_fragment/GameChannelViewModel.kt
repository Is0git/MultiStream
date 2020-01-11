package com.android.multistream.ui.game_channels_fragment

import androidx.lifecycle.ViewModel
import com.android.multistream.di.MainActivity.game_channels_fragment.GameChannelsFragmentScope
import javax.inject.Inject

@GameChannelsFragmentScope
class GameChannelViewModel @Inject constructor(repo: GameChannelsRepository) : ViewModel()