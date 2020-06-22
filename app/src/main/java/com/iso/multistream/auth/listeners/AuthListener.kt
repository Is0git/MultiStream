package com.iso.multistream.auth.listeners

interface AuthListener {
    fun onSuccess()

    fun failed(throwable: Throwable)
}