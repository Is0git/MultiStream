package com.iso.multistream.ui.main_activity.fragments.browse_fragment.view_pager_fragments.mixer_fragment

import androidx.lifecycle.ViewModel
import com.iso.multistream.di.main_activity.main_fragments.browse_fragment.view_pager_fragments.mixer_fragment.MixerGamesBrowseFragmentScope
import javax.inject.Inject

@MixerGamesBrowseFragmentScope
class MixerGamesBrowseViewModel @Inject constructor(val repo: MixerGamesBrowseRepository) :
    ViewModel() {
    val pageLoader = repo.pageLoader
}