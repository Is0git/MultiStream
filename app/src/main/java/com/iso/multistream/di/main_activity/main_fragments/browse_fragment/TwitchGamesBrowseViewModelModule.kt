package com.iso.multistream.di.main_activity.main_fragments.browse_fragment

import androidx.lifecycle.ViewModel
import com.iso.multistream.di.main_activity.ViewModelKey
import com.iso.multistream.di.main_activity.main_fragments.browse_fragment.view_pager_fragments.twitch_fragment.TwitchGamesBrowseFragmentScope
import com.iso.multistream.ui.main_activity.fragments.browse_fragment.view_pager_fragments.twitch_fragment.TwitchGamesBrowseViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class TwitchGamesBrowseViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(TwitchGamesBrowseViewModel::class)
    @TwitchGamesBrowseFragmentScope
    abstract fun browseViewModel(browseFragmentViewModel: TwitchGamesBrowseViewModel): ViewModel
}