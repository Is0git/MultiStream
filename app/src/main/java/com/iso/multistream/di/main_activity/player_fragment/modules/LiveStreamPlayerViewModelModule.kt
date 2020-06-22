package com.iso.multistream.di.main_activity.player_fragment.modules

import androidx.lifecycle.ViewModel
import com.iso.multistream.di.main_activity.ViewModelKey
import com.iso.multistream.di.main_activity.player_fragment.PlayerFragmentScope
import com.iso.multistream.ui.player.fragments.live_stream_player_fragment.LiveStreamPlayerViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class LiveStreamPlayerViewModelModule {
    @Binds
    @IntoMap
    @PlayerFragmentScope
    @ViewModelKey(LiveStreamPlayerViewModel::class)
    abstract fun getPlayerViewModel(viewModel: LiveStreamPlayerViewModel) : ViewModel
}