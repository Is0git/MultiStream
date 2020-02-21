package com.android.multistream.ui.main.fragments.browse_fragment.view_pager_fragments.top_fragment

import androidx.lifecycle.ViewModel
import com.android.multistream.di.main_activity.main_fragments.browse_fragment.view_pager_fragments.top_fragment.TopFragmentGamesScope
import javax.inject.Inject

@TopFragmentGamesScope
class TopFragmentViewModel @Inject constructor(val repo: TopFragmentRepository) : ViewModel() {

    val pageLiveData = repo.pageLoader.dataLiveData

    fun loadPage() {
        repo.pageLoader.loadHandler()
    }

    fun getPaginationSate() = repo.pageLoader.pageLoadingState.value
}