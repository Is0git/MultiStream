package com.android.multistream.utils

import android.app.Application
import android.content.Context
import com.android.multistream.R

object NumbersConverter {

    fun getK(value: Int?, context: Context) : String {
        val res = if (value == null) 0f else value / 1000f
        return context.getString(R.string.k_value, res)
    }
}