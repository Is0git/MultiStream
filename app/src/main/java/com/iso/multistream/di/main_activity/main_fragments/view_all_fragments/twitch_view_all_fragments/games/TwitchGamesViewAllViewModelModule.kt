package com.iso.multistream.di.main_activity.main_fragments.view_all_fragments.twitch_view_all_fragments.games

import androidx.lifecycle.ViewModel
import com.iso.multistream.di.main_activity.ViewModelKey
import com.iso.multistream.di.main_activity.main_fragments.view_all_fragments.ViewAllFragmentScope
import com.iso.multistream.ui.main_activity.fragments.view_all_fragments.twitch.games_view_all.TwitchGamesViewAllViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class TwitchGamesViewAllViewModelModule {

    @Binds
    @IntoMap
    @ViewAllFragmentScope
    @ViewModelKey(TwitchGamesViewAllViewModel::class)
    abstract fun getViewModel(viewModel: TwitchGamesViewAllViewModel) : ViewModel
}