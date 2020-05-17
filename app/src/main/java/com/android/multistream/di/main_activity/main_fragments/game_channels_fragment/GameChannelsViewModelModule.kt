package com.android.multistream.di.main_activity.main_fragments.game_channels_fragment

import androidx.lifecycle.ViewModel
import com.android.multistream.di.main_activity.ViewModelKey
import com.android.multistream.ui.main.fragments.game_channels_fragment.GameChannelViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class GameChannelsViewModelModule {
    @Binds
    @IntoMap
    @GameChannelsFragmentScope
    @ViewModelKey(GameChannelViewModel::class)
    abstract fun bindViewModel(viewModel: GameChannelViewModel): ViewModel
}