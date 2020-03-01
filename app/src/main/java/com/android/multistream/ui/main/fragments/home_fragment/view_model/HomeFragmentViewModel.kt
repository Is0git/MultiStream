package com.android.multistream.ui.main.fragments.home_fragment.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.multistream.di.main_activity.main_fragments.home_fragment.HomeFragmentScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
@HomeFragmentScope
class HomeFragmentViewModel @Inject constructor(val repo: HomeFragmentRepository) : ViewModel() {

    init {
        getChannels()
        getFollowedStreams("live")
    }
    val topChannelsLiveData = repo.topChannelsLiveData

    val followedStreamsLiveData = repo.followedStreamsLiveData

    fun getChannels() {
        viewModelScope.launch { repo.getTopChannels() }
    }
    fun getFollowedStreams(type: String) {
        viewModelScope.launch(Dispatchers.IO) { repo.getFollowedLiveStreams(type) }
    }

}