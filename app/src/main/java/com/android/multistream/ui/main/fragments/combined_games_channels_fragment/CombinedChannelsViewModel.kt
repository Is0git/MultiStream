package com.android.multistream.ui.main.fragments.combined_games_channels_fragment

import androidx.lifecycle.ViewModel
import com.android.multistream.di.main_activity.main_fragments.combined_channels_fragment.CombinedChannelsScope
import javax.inject.Inject

@CombinedChannelsScope
class CombinedChannelsViewModel @Inject constructor(val repo: CombinedChannelsRepository) :
    ViewModel()