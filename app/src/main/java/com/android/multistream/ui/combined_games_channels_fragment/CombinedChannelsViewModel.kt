package com.android.multistream.ui.combined_games_channels_fragment

import androidx.lifecycle.ViewModel
import com.android.multistream.di.MainActivity.combined_channels_fragment.CombinedChannelsScope
import javax.inject.Inject
@CombinedChannelsScope
class CombinedChannelsViewModel @Inject constructor(val repo: CombinedChannelsRepository) : ViewModel() {
}