package com.iso.multistream.ui.main_activity.fragments.home_fragment.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iso.multistream.di.main_activity.main_fragments.home_fragment.HomeFragmentScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HomeFragmentScope
class HomeFragmentViewModel @Inject constructor(val repo: HomeFragmentRepository) : ViewModel() {

    val topChannelsLiveData = repo.topChannelsLiveData
    val followedLiveStreamsLiveData = repo.followedLiveStreamsLiveData
    val followedStreamsLiveData = repo.followedStreamsLiveData
    val topGamesLiveData = repo.topGamesLiveData
    val mixerTopGamesLiveData = repo.mixerTopGamesLiveData
    val mixerTopChannelsLiveData = repo.mixerTopChannelsLiveData

    fun getChannels() {
        viewModelScope.launch {
            repo.getTopChannels() }
    }

    fun getFollowedLiveStreams(type: String) {
        viewModelScope.launch(Dispatchers.IO) { repo.getFollowedLiveStreams(type) }
    }

    fun getFollowedStreams(type: String) {
        viewModelScope.launch(Dispatchers.IO) { repo.getFollowedStreams(type) }
    }

    fun getTopGames(limit: Int) {
        viewModelScope.launch(Dispatchers.IO) { repo.getTopGames(limit) }
    }

    fun getMixerTopGames(limit: Int) {
        viewModelScope.launch { repo.getMixerTopGames(limit) }
    }

   fun getMixerTopChannels(pageLimit: Int) {
        viewModelScope.launch { repo.getMixerTopChannels(pageLimit) }
    }
}