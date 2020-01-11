package com.android.multistream.ui.browse_fragment.view_pager_fragments.twitch_fragment

import androidx.lifecycle.ViewModel
import com.android.multistream.di.MainActivity.browse_fragment.view_pager_fragments.twitch_fragment.TwitchFragmentGamesScope
import javax.inject.Inject

@TwitchFragmentGamesScope
class TwitchFragmentViewModel @Inject constructor(repo: TwitchFragmentRepository) : ViewModel() {
}