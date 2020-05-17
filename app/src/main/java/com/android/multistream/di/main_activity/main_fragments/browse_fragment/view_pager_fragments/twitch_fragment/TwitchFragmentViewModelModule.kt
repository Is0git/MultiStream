package com.android.multistream.di.main_activity.main_fragments.browse_fragment.view_pager_fragments.twitch_fragment

import androidx.lifecycle.ViewModel
import com.android.multistream.di.main_activity.ViewModelKey
import com.android.multistream.ui.main.fragments.browse_fragment.view_pager_fragments.twitch_fragment.TwitchGamesBrowseViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class TwitchFragmentViewModelModule {

    @Binds
    @IntoMap
    @TwitchGamesBrowseFragmentScope
    @ViewModelKey(TwitchGamesBrowseViewModel::class)
    abstract fun bind(viewModel: TwitchGamesBrowseViewModel): ViewModel
}