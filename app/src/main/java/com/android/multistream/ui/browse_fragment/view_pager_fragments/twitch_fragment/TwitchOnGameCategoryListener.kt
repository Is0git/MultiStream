package com.android.multistream.ui.browse_fragment.view_pager_fragments.twitch_fragment

interface TwitchOnGameCategoryListener{
    fun onGameClick(platformType: Int, viewers: Int, channels: String?, name: String, boxImage: String?)
}