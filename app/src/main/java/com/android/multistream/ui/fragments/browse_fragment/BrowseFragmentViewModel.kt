package com.android.multistream.ui.fragments.browse_fragment

import androidx.lifecycle.ViewModel
import com.android.multistream.di.MainActivity.browse_fragment.BrowseFragmentScope
import javax.inject.Inject

@BrowseFragmentScope
class BrowseFragmentViewModel @Inject constructor(val repo: BrowseFragmentRepository) : ViewModel() {

//    var paginationListener = repo.listener

}