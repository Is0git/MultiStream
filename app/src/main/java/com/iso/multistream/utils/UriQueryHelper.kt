package com.iso.multistream.utils

//for twitch only
fun uriQuery(uri: String): String? {
    val regex = """\w{30}""".toRegex()
    val matcher = regex.find(uri)
    return matcher?.value
}

fun getMixerImageUrl(channelId: Int?) : String {
    return "https://thumbs.mixer.com/channel/$channelId.big.jpg"
}