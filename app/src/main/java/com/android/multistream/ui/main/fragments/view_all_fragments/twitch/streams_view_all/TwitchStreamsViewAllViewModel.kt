package com.android.multistream.ui.main.fragments.view_all_fragments.twitch.streams_view_all

import androidx.lifecycle.ViewModel
import com.android.multistream.di.main_activity.main_fragments.view_all_fragments.ViewAllFragmentScope
import javax.inject.Inject

@ViewAllFragmentScope
class TwitchStreamsViewAllViewModel @Inject constructor(var repo: TwitchStreamsAllViewRepository) : ViewModel() {

    val pageLoader = repo.pageLoader
}