package com.android.multistream.ui.main_activity.fragments.view_all_fragments.twitch.games_view_all

import androidx.lifecycle.ViewModel
import com.android.multistream.di.main_activity.main_fragments.view_all_fragments.ViewAllFragmentScope
import javax.inject.Inject

@ViewAllFragmentScope
class TwitchGamesViewAllViewModel @Inject constructor(var repo : TwitchGamesViewAllRepository): ViewModel(){

    val pageLoader = repo.pageLoader

}