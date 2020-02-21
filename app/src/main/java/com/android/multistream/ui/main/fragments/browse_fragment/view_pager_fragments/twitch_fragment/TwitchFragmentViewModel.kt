package com.android.multistream.ui.main.fragments.browse_fragment.view_pager_fragments.twitch_fragment

import androidx.lifecycle.ViewModel
import com.android.multistream.di.main_activity.main_fragments.browse_fragment.view_pager_fragments.twitch_fragment.TwitchFragmentGamesScope
import javax.inject.Inject

@TwitchFragmentGamesScope
class TwitchFragmentViewModel @Inject constructor(val repo: TwitchFragmentRepository) : ViewModel() {
    val paginationLiveData = repo.pagedOffSetLoader.dataLiveData
   fun loadPage() {repo.pagedOffSetLoader.loadHandler()}

    fun getPaginationState() = repo.pagedOffSetLoader.pageLoadingState.value
}