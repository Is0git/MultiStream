package com.android.multistream.ui.browse_fragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.multistream.di.MainActivity.browse_fragment.BrowseFragmentScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@BrowseFragmentScope
class BrowseFragmentViewModel @Inject constructor(val repo: BrowseFragmentRepository) : ViewModel() {
    val topGamesPagedListLiveData = repo.topGames
    var paginationListener = repo.listener

//    private fun updateTopGames() {
//       viewModelScope.launch {  repo.updateTopGames() }
//    }
}