package com.android.multistream.utils

import android.app.Application
import android.content.Context
import com.android.multistream.R

object NumbersConverter {

    fun getK(value: Int?, context: Context): String {
        return when {
            value == null -> "0"
            value < 1000000 ->  {
                val res = value / 1000f
                context.getString(R.string.k_value, res)
            }
            else -> {
                val res = value / 1000000f
                context.getString(R.string.millions_value, res)
            }
        }
    }
}