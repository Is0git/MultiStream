package com.android.multistream.ui.browse_fragment.view_pager_fragments.mixer_fragment

import androidx.lifecycle.ViewModel
import com.android.multistream.di.MainActivity.browse_fragment.view_pager_fragments.mixer_fragment.MixerFragmentGamesScope
import javax.inject.Inject

@MixerFragmentGamesScope
class MixerFragmentViewModel @Inject constructor(val repo: MixerFragmentRepository) : ViewModel() {

    val pagedOffSetListener = repo.topGamesPaginationListener
}