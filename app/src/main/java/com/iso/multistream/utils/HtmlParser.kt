package com.iso.multistream.utils

import android.content.Context
import android.os.Build
import android.text.Html
import android.text.Spanned
import androidx.annotation.StringRes

object HtmlParser {
    fun getStringFromHtml(@StringRes htmlStringRes: Int, context: Context) : Spanned {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Html.fromHtml(context.getString(htmlStringRes), Html.FROM_HTML_MODE_COMPACT)
        } else {
            Html.fromHtml(context.getString(htmlStringRes))
        }
    }
}