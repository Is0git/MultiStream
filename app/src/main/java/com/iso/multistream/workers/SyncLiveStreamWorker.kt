package com.iso.multistream.workers

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.ListenableWorker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.iso.multistream.di.main_activity.player_fragment.PlayerFragmentScope
import com.iso.multistream.network.twitch.TwitchService
import com.iso.multistream.utils.ResponseHandler.execute
import com.iso.multistream.utils.WorkerCreator
import kotlinx.coroutines.coroutineScope
import javax.inject.Inject
import javax.inject.Singleton

@PlayerFragmentScope
class SyncLiveStreamWorker @Inject constructor(
    var context: Context,
    private val params: WorkerParameters,
    private val twitchService: TwitchService) : CoroutineWorker(context, params) {
    override suspend fun doWork(): Result = coroutineScope {
        val channelId = params.inputData.getString("channel_id")
        val result = execute(context) {twitchService.getStream(channelId!!)}
        if (result == null) Result.failure()
        val data = workDataOf("viewers_count" to result?.stream?.viewers, "title" to result?.stream?.channel?.status)
        Result.success(data)
    }
}

@Singleton
class SyncLiveStreamWorkerCreator @Inject constructor(val twitchService: TwitchService) :
    WorkerCreator {

    override fun create(appContext: Context, params: WorkerParameters): ListenableWorker {
        return SyncLiveStreamWorker(appContext, params, twitchService)
    }
}