package com.android.multistream.ui.fragments.home_fragment.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.multistream.di.MainActivity.home_fragment.HomeFragmentScope
import com.android.multistream.ui.fragments.home_fragment.view_model.HomeFragmentRepository
import kotlinx.coroutines.launch
import javax.inject.Inject
@HomeFragmentScope
class HomeFragmentViewModel @Inject constructor(val repo: HomeFragmentRepository) : ViewModel() {

    init {
        getChannels()
    }
    val topChannelsLiveData = repo.topChannelsLiveData

    fun getChannels() {
        viewModelScope.launch { repo.getTopChannels() }
    }
}