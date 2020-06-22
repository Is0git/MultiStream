package com.iso.multistream.utils

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton

@Singleton
class WorkFactory @Inject constructor(val workerFactories: Map<Class<out ListenableWorker>, @JvmSuppressWildcards Provider<WorkerCreator>>) :
    WorkerFactory() {
    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters
    ): ListenableWorker? {
        val creator = workerFactories[Class.forName(workerClassName)]
            ?: throw IllegalArgumentException("no workers")
        return creator.get().create(appContext, workerParameters)
    }
}