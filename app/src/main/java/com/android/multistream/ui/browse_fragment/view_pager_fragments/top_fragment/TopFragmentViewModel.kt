package com.android.multistream.ui.browse_fragment.view_pager_fragments.top_fragment

import androidx.lifecycle.ViewModel
import com.android.multistream.di.MainActivity.browse_fragment.view_pager_fragments.top_fragment.TopFragmentGamesScope
import javax.inject.Inject

@TopFragmentGamesScope
class TopFragmentViewModel @Inject constructor(val repo: TopFragmentRepository) : ViewModel() {

    val listener = repo.pageListener
}