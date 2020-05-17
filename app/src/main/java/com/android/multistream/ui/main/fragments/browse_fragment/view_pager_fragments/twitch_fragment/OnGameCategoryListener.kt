package com.android.multistream.ui.main.fragments.browse_fragment.view_pager_fragments.twitch_fragment

interface OnGameCategoryListener {
    fun onGameClick(
        platformType: Int,
        viewers: Int,
        channels: String?,
        name: String,
        boxImage: String?,
        gameId: String? = null
    )
}