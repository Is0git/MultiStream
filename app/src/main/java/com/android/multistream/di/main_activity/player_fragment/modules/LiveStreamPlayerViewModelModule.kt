package com.android.multistream.di.main_activity.player_fragment.modules

import androidx.lifecycle.ViewModel
import com.android.multistream.di.main_activity.ViewModelKey
import com.android.multistream.di.main_activity.player_fragment.PlayerFragmentScope
import com.android.multistream.ui.player.fragments.live_stream_player_fragment.LiveStreamPlayerViewModel
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