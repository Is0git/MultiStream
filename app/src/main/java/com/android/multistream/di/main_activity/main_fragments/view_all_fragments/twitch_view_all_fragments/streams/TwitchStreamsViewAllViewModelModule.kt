package com.android.multistream.di.main_activity.main_fragments.view_all_fragments.twitch_view_all_fragments.streams

import androidx.lifecycle.ViewModel
import com.android.multistream.di.main_activity.ViewModelKey
import com.android.multistream.di.main_activity.main_fragments.view_all_fragments.ViewAllFragmentScope
import com.android.multistream.ui.main.fragments.view_all_fragments.twitch.games_view_all.TwitchGamesViewAllViewModel
import com.android.multistream.ui.main.fragments.view_all_fragments.twitch.streams_view_all.TwitchStreamsViewAllViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class TwitchStreamsViewAllViewModelModule {

    @Binds
    @IntoMap
    @ViewAllFragmentScope
    @ViewModelKey(TwitchStreamsViewAllViewModel::class)
    abstract fun getViewModel(viewModel: TwitchStreamsViewAllViewModel) : ViewModel
}