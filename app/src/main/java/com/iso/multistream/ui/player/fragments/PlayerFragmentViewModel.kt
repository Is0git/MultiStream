package com.iso.multistream.ui.player.fragments

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

abstract class PlayerFragmentViewModel<T : PlayerFragmentRepository>(var repository: T) :
    ViewModel(), LifecycleObserver {

    val followUser = repository.followsLiveData
    val userLiveData = repository.userLiveData

    fun getStream(channelId: String) {
        viewModelScope.launch { repository.getStream(channelId) }
    }

    fun getFollowUser(channelId: String) {
        viewModelScope.launch { repository.getFollowUser(channelId) }
    }

}