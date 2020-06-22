package com.iso.multistream.utils

import android.content.Context
import android.net.ConnectivityManager
import android.util.Log
import android.widget.Toast
import retrofit2.Response
import java.io.IOException

object ResponseHandler {

    private const val RETROFIT_RESPONSE_TAG = "RETROFIT_RESPONSE"

    suspend fun handleResponse(response: Response<*>, appContext: Context?): Boolean {
        return response.run {
            when {
                isSuccessful -> onSuccess(response)
                else -> onFailed(response, appContext)
            }
        }
    }

    private suspend fun onFailed(response: Response<*>, appContext: Context?): Boolean {
        val message = when (response.code()) {
            in 500..599 -> "unauthorized, sync your accounts"
            in 400..500 -> "the request is not available right now or doesn't exist"
            else -> "Something went wrong.."
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

    suspend fun handleNetworkException(exception: Exception, appContext: Context?) {
        when (exception) {
            is IOException -> {
                Log.i( RETROFIT_RESPONSE_TAG, "trouble making a request")
            }
            is NoInternetException -> {
                Log.i( RETROFIT_RESPONSE_TAG, "no internet")

            }
            else ->  Log.i( RETROFIT_RESPONSE_TAG, "${exception.message}")
        }
    }

    suspend fun <T> execute(appContext: Context, action: suspend () -> Response<T>): T? {
        try {
            val connectivityManager =
                appContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
                val connected =ConnectivityHelper.checkIfConnectedToInternet(connectivityManager)
            if (!connected) Toast.makeText(
                appContext,
                "Connect to internet",
                Toast.LENGTH_SHORT
            ).show()
            val result = action()
            if (handleResponse(result, appContext)) return result.body()
        } catch (ex: Exception) {
            handleNetworkException(ex, appContext)
        }
        return null
    }
}

class NoInternetException : Exception() {
    override val message: String? = "Connect to the internet"
}