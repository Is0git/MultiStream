package com.android.multistream.ui.fragments.browse_fragment.view_pager_fragments.mixer_fragment

import androidx.lifecycle.ViewModel
import com.android.multistream.di.MainActivity.browse_fragment.view_pager_fragments.mixer_fragment.MixerFragmentGamesScope
import javax.inject.Inject

@MixerFragmentGamesScope
class MixerFragmentViewModel @Inject constructor(val repo: MixerFragmentRepository) : ViewModel() {
    val pageLiveData = repo.mixerTopGamesPagination.dataLiveData

    fun loadPage() {repo.mixerTopGamesPagination.loadHandler()}

    fun getState() = repo.mixerTopGamesPagination.pageLoadingState.value
}