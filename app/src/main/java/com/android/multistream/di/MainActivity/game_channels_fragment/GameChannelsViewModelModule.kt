package com.android.multistream.di.MainActivity.game_channels_fragment

import androidx.lifecycle.ViewModel
import com.android.multistream.di.MainActivity.ViewModelKey
import com.android.multistream.ui.game_channels_fragment.GameChannelViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class GameChannelsViewModelModule {
    @Binds
    @IntoMap
    @GameChannelsFragmentScope
    @ViewModelKey(GameChannelViewModel::class)
    abstract fun bindViewModel(viewModel: GameChannelViewModel) : ViewModel
}