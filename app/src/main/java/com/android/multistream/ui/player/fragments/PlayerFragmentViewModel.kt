package com.android.multistream.ui.player.fragments

import androidx.lifecycle.*
import androidx.work.WorkInfo
import com.android.multistream.di.main_activity.player_fragment.PlayerFragmentScope
import kotlinx.coroutines.launch
import javax.inject.Inject

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