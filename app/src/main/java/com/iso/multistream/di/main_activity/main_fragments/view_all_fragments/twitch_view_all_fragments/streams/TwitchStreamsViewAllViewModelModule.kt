package com.iso.multistream.di.main_activity.main_fragments.view_all_fragments.twitch_view_all_fragments.streams

import androidx.lifecycle.ViewModel
import com.iso.multistream.di.main_activity.ViewModelKey
import com.iso.multistream.di.main_activity.main_fragments.view_all_fragments.ViewAllFragmentScope
import com.iso.multistream.ui.main_activity.fragments.view_all_fragments.twitch.streams_view_all.TwitchStreamsViewAllViewModel
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