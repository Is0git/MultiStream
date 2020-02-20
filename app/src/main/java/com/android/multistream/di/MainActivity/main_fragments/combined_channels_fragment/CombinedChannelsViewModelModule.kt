package com.android.multistream.di.MainActivity.main_fragments.combined_channels_fragment

import androidx.lifecycle.ViewModel
import com.android.multistream.di.MainActivity.ViewModelKey
import com.android.multistream.ui.main.fragments.combined_games_channels_fragment.CombinedChannelsViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class CombinedChannelsViewModelModule {
    @Binds
    @IntoMap
    @CombinedChannelsScope
    @ViewModelKey(CombinedChannelsViewModel::class)
    abstract fun bind(viewModel: CombinedChannelsViewModel) : ViewModel
}