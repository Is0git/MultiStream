package com.iso.multistream.di.main_activity.main_fragments.view_all_fragments.twitch_view_all_fragments.channels

import androidx.lifecycle.ViewModel
import com.iso.multistream.di.main_activity.ViewModelKey
import com.iso.multistream.di.main_activity.main_fragments.view_all_fragments.ViewAllFragmentScope
import com.iso.multistream.ui.main_activity.fragments.view_all_fragments.twitch.channels_view_all.TwitchChannelsViewAllViewModel
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