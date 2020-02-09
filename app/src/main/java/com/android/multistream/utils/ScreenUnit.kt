package com.android.multistream.utils

import android.content.res.Resources
import android.util.DisplayMetrics
import kotlin.math.roundToInt


object  ScreenUnit {
    fun convertDpToPixel(dp: Float): Int {
        val metrics: DisplayMetrics = Resources.getSystem().displayMetrics
        val px = dp * (metrics.densityDpi / 160f)
        return px.roundToInt()
    }
}