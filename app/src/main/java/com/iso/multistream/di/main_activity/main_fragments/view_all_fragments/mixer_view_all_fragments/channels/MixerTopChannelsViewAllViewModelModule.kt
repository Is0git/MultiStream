package com.iso.multistream.di.main_activity.main_fragments.view_all_fragments.mixer_view_all_fragments.channels

import androidx.lifecycle.ViewModel
import com.iso.multistream.di.main_activity.ViewModelKey
import com.iso.multistream.di.main_activity.main_fragments.view_all_fragments.ViewAllFragmentScope
import com.iso.multistream.ui.main_activity.fragments.view_all_fragments.mixer.top_channels_view_all.MixerTopChannelsViewAllViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class MixerTopChannelsViewAllViewModelModule {

    @Binds
    @IntoMap
    @ViewAllFragmentScope
    @ViewModelKey(MixerTopChannelsViewAllViewModel::class)
    abstract fun getTopChannelsViewModel(viewModel: MixerTopChannelsViewAllViewModel) : ViewModel
}