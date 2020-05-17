package com.android.multistream.utils

import android.app.Application
import android.util.Log
import android.widget.Toast
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import java.io.IOException

object ResponseHandler {

    private const val RETROFIT_RESPONSE_TAG = "RETROFIT_RESPONSE"

    suspend fun handleResponse(response: Response<*>, appContext: Application?): Boolean {
        return response.run {
            when {
                isSuccessful && body() != null -> onSuccess(response)
                else -> onFailed(response, appContext)
            }
        }
    }

    private suspend fun onFailed(response: Response<*>, appContext: Application?): Boolean {
        val message = when (response.code()) {
            in 500..599 -> "unauthorized, sync your accounts"
            in 400..500 -> "the request is not available right now or doesn't exist"
            else -> "Something went wrong.."
        }
        appContext?.also {
            withContext(Dispatchers.Main) {
                Toast.makeText(
                    it,
                    "${response.code()}: $message",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        Log.e(
            RETROFIT_RESPONSE_TAG,
            "request with URL: ${response.raw().request.url} failed due to ${response.message()}"
        )
        return false
    }

    private fun onSuccess(response: Response<*>): Boolean {
        Log.i(RETROFIT_RESPONSE_TAG, "request with URL: ${response.raw().request.url} SUCCESSFUL")
        return true
    }

    fun handleNetworkException(exception: Exception, appContext: Application?) {
        when (exception) {
            is IOException -> {
                appContext?.also { Toast.makeText(it, "NO INTERNET", Toast.LENGTH_SHORT).show() }
            }
        }
    }

    suspend fun <T> execute(appContext: Application, action: suspend () -> Response<T>): T? {
        try {
            val result = action()
            if (handleResponse(result, appContext)) return result.body()
        } catch (ex: Exception) {
            handleNetworkException(ex, appContext)
        }
        return null
    }
}