package com.android.multistream.auth

interface AuthListener {
    fun onSuccess()

    fun cancel(throwable: Throwable)
}