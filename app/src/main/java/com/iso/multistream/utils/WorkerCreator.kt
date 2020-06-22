package com.iso.multistream.utils

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerParameters

interface WorkerCreator {
    fun create(appContext: Context, params: WorkerParameters): ListenableWorker

}