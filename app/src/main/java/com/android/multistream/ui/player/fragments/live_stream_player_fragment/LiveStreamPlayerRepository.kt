package com.android.multistream.ui.player.fragments.live_stream_player_fragment

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.work.*
import com.android.multistream.auth.platform_manager.PlatformManager
import com.android.multistream.di.main_activity.player_fragment.PlayerFragmentScope
import com.android.multistream.network.twitch.TwitchService
import com.android.multistream.network.twitch.models.v5.single_stream.SingleStream
import com.android.multistream.ui.player.fragments.PlayerFragmentRepository
import com.android.multistream.utils.ResponseHandler
import com.android.multistream.utils.WorkFactory
import com.android.multistream.workers.SyncLiveStreamWorker
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@PlayerFragmentScope
class LiveStreamPlayerRepository @Inject constructor(app: Application, twitchService: TwitchService, platformManager: PlatformManager): PlayerFragmentRepository(app, twitchService, platformManager) {

    var workManager: WorkManager = WorkManager.getInstance(app)

    fun startSyncWork(id: String) {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()
        val data = Data.Builder().putString("channel_id", id).build()
        val liveStreamSyncPeriodicWork =
            PeriodicWorkRequestBuilder<SyncLiveStreamWorker>(2, TimeUnit.MINUTES)
                .setConstraints(constraints)
                .setInputData(data)
                .build()
        workManager.enqueueUniquePeriodicWork(
            "live_stream_sync_work",
            ExistingPeriodicWorkPolicy.KEEP,
            liveStreamSyncPeriodicWork
        )
    }

    fun getLiveStreamPeriodicWorkLiveData(): LiveData<WorkInfo?> {
        val workListData = workManager.getWorkInfosByTagLiveData("live_stream_sync_work")
        return Transformations.map(workListData) { input: MutableList<WorkInfo>? -> input?.firstOrNull() }
    }

    fun cancelLiveStreamSyncWorker() {
        workManager.cancelAllWorkByTag("live_stream_sync_work")
    }

}