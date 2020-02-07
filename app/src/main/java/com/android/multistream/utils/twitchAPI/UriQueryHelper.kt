package com.android.multistream.utils.twitchAPI

fun uriQuery(uri: String) : String? {
    val regex = """\w{30}""".toRegex()
    val matcher = regex.find(uri)
    return matcher?.value
}