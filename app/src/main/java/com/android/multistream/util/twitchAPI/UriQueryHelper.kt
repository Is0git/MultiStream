package com.android.multistream.util.twitchAPI

fun uriQuery(uri: String) : String? {
    val regex = """\w{30}""".toRegex()
    val matcher = regex.find(uri)
    return matcher?.value
}