package com.iso.multistream.ui.main_activity.fragments.view_all_fragments.mixer.top_games_view_all

import androidx.lifecycle.ViewModel
import com.iso.multistream.di.main_activity.main_fragments.view_all_fragments.ViewAllFragmentScope
import javax.inject.Inject

@ViewAllFragmentScope
class MixerTopGamesViewAllViewModel @Inject constructor(var repo: MixerTopGamesViewAllRepository) : ViewModel() {

    var pageLoader = repo.pageLoader
}