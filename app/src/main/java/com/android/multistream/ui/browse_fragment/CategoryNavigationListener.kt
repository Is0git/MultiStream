package com.android.multistream.ui.browse_fragment

import com.android.multistream.network.twitch.models.Data

interface CategoryNavigationListener {

    fun onGameClick(data: Data)


}