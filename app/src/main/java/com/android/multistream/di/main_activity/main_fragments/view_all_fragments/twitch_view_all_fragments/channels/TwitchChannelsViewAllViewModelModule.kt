package com.android.multistream.di.main_activity.main_fragments.view_all_fragments.twitch_view_all_fragments.channels

import androidx.lifecycle.ViewModel
import com.android.multistream.di.main_activity.ViewModelKey
import com.android.multistream.di.main_activity.main_fragments.view_all_fragments.ViewAllFragmentScope
import com.android.multistream.ui.main.fragments.view_all_fragments.twitch.channels_view_all.TwitchChannelsViewAllViewModel
import com.android.multistream.ui.main.fragments.view_all_fragments.twitch.games_view_all.TwitchGamesViewAllViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class TwitchChannelsViewAllViewModelModule {

    @Binds
    @IntoMap
    @ViewAllFragmentScope
    @ViewModelKey(TwitchChannelsViewAllViewModel::class)
    abstract fun getViewModel(viewModel: TwitchChannelsViewAllViewModel) : ViewModel
}