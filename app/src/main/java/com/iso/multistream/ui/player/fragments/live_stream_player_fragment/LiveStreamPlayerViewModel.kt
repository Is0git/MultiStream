package com.iso.multistream.ui.player.fragments.live_stream_player_fragment

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LiveData
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.viewModelScope
import androidx.work.WorkInfo
import com.iso.multistream.di.main_activity.player_fragment.PlayerFragmentScope
import com.iso.multistream.network.twitch.models.v5.single_stream.SingleStream
import com.iso.multistream.ui.player.fragments.PlayerFragmentViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@PlayerFragmentScope
class LiveStreamPlayerViewModel @Inject constructor(var repo: LiveStreamPlayerRepository) : PlayerFragmentViewModel<LiveStreamPlayerRepository>(repo) {

    var streamLiveData: LiveData<SingleStream?> = repo.streamDataMutableLiveData

    fun getLiveStreamPeriodicWorkLiveData(): LiveData<WorkInfo?> {
        return repo.getLiveStreamPeriodicWorkLiveData()
    }

    fun startSyncPeriodicWork(channelId: String) {
        viewModelScope.launch {
            repo.startSyncWork(channelId)
        }
    }

    fun getStreamData(channelName: String) {
            viewModelScope.launch(Dispatchers.IO) { repo.getStreamData(channelName) }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun cancelLiveStreamSyncWorker() {
//        repo.cancelLiveStreamSyncWorker()
    }
}