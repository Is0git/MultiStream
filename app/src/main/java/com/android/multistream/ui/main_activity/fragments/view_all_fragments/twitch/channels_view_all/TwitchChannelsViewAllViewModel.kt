package com.android.multistream.ui.main_activity.fragments.view_all_fragments.twitch.channels_view_all

import androidx.lifecycle.ViewModel
import com.android.multistream.di.main_activity.main_fragments.view_all_fragments.ViewAllFragmentScope
import javax.inject.Inject

@ViewAllFragmentScope
class TwitchChannelsViewAllViewModel @Inject constructor(val repo: TwitchChannelsViewAllRepository) : ViewModel() {

    val pageLoader = repo.pageLoader
}