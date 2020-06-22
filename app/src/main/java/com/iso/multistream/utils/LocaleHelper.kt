package com.iso.multistream.utils

import android.content.res.Configuration
import android.content.res.Resources
import android.util.DisplayMetrics
import java.util.*


fun setLocale(lang: String? = null, resources: Resources) {
    var myLocale = Locale(lang)
    val res: Resources = resources
    val dm: DisplayMetrics = res.displayMetrics
    val conf: Configuration = res.configuration
    conf.setLocale(myLocale)
    res.updateConfiguration(conf, dm)
}