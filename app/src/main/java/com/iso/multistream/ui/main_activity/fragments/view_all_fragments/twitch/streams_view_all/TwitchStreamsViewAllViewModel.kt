package com.iso.multistream.ui.main_activity.fragments.view_all_fragments.twitch.streams_view_all

import androidx.lifecycle.ViewModel
import com.iso.multistream.di.main_activity.main_fragments.view_all_fragments.ViewAllFragmentScope
import javax.inject.Inject

@ViewAllFragmentScope
class TwitchStreamsViewAllViewModel @Inject constructor(var repo: TwitchStreamsAllViewRepository) : ViewModel() {
    val pageLoader = repo.pageLoader
}