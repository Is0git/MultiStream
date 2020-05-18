package com.android.multistream.auth.listeners

interface AuthListener {
    fun onSuccess()

    fun failed(throwable: Throwable)
}