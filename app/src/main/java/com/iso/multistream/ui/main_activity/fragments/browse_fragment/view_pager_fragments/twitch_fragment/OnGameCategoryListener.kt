package com.iso.multistream.ui.main_activity.fragments.browse_fragment.view_pager_fragments.twitch_fragment

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