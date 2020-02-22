package com.android.multistream.auth

interface AuthListener {
    fun onSuccess()

    fun failed(throwable: Throwable)
}