package com.android.multistream.ui.main.fragments.browse_fragment.view_pager_fragments.top_fragment

import android.app.Application
import android.widget.Toast
import com.android.multistream.di.main_activity.main_fragments.browse_fragment.view_pager_fragments.top_fragment.TopFragmentGamesScope
import com.android.multistream.network.mixer.MixerService
import com.android.multistream.network.twitch.TwitchService
import com.android.multistream.network.twitch.models.new_twitch_api.top_games.TopGame
import com.android.multistream.pagination.listeners.PagedOffSetListener
import com.android.multistream.pagination.PagedOffsetLoader
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*


import java.io.IOException
import javax.inject.Inject

@TopFragmentGamesScope
class TopFragmentRepository @Inject constructor(
    val application: Application,
    val twitchService: TwitchService,
    val mixerService: MixerService
)  {
    var loadJob: Job? = null
}