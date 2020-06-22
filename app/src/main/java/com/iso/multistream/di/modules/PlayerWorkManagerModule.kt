package com.iso.multistream.di.modules

import com.iso.multistream.di.keys.WorkerKey
import com.iso.multistream.utils.WorkerCreator
import com.iso.multistream.workers.SyncLiveStreamWorker
import com.iso.multistream.workers.SyncLiveStreamWorkerCreator
import dagger.Binds
import dagger.Module
import dagger.Reusable
import dagger.multibindings.IntoMap

@Module
abstract class PlayerWorkManagerModule {
    @Binds
    @IntoMap
    @Reusable
    @WorkerKey(SyncLiveStreamWorker::class)
    abstract fun getLiveStreamWorkerCreator(creator: SyncLiveStreamWorkerCreator) : WorkerCreator
}