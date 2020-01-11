package com.android.multistream.di.MainActivity.browse_fragment.view_pager_fragments.twitch_fragment

import androidx.lifecycle.ViewModel
import com.android.multistream.di.MainActivity.ViewModelKey
import com.android.multistream.ui.browse_fragment.view_pager_fragments.twitch_fragment.TwitchFragmentViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class TwitchFragmentViewModelModule {

    @Binds
    @IntoMap
    @TwitchFragmentGamesScope
    @ViewModelKey(TwitchFragmentViewModel::class)
    abstract fun bind(viewModel: TwitchFragmentViewModel) : ViewModel
}