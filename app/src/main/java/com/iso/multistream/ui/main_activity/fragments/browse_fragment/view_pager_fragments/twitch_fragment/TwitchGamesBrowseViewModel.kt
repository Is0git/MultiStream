package com.iso.multistream.ui.main_activity.fragments.browse_fragment.view_pager_fragments.twitch_fragment

import androidx.lifecycle.ViewModel
import com.iso.multistream.di.main_activity.main_fragments.browse_fragment.view_pager_fragments.twitch_fragment.TwitchGamesBrowseFragmentScope
import javax.inject.Inject

@TwitchGamesBrowseFragmentScope
class TwitchGamesBrowseViewModel @Inject constructor(val repo: TwitchGamesBrowseRepository) :
    ViewModel() {

    var pageLoader = repo.pageLoader

}