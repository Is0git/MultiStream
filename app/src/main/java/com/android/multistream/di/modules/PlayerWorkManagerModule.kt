package com.android.multistream.di.modules

import androidx.work.ListenableWorker
import com.android.multistream.di.keys.WorkerKey
import com.android.multistream.utils.WorkerCreator
import com.android.multistream.workers.SyncLiveStreamWorker
import com.android.multistream.workers.SyncLiveStreamWorkerCreator
import dagger.Binds
import dagger.Module
import dagger.Reusable
import dagger.multibindings.IntoMap
import javax.inject.Singleton

@Module
abstract class PlayerWorkManagerModule {
    @Binds
    @IntoMap
    @Reusable
    @WorkerKey(SyncLiveStreamWorker::class)
    abstract fun getLiveStreamWorkerCreator(creator: SyncLiveStreamWorkerCreator) : WorkerCreator
}