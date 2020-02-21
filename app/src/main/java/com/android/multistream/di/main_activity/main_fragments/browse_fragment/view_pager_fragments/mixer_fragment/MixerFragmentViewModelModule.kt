package com.android.multistream.di.main_activity.main_fragments.browse_fragment.view_pager_fragments.mixer_fragment

import androidx.lifecycle.ViewModel
import com.android.multistream.di.main_activity.ViewModelKey
import com.android.multistream.ui.main.fragments.browse_fragment.view_pager_fragments.mixer_fragment.MixerFragmentViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class MixerFragmentViewModelModule {
    @Binds
    @IntoMap
    @MixerFragmentGamesScope
    @ViewModelKey(MixerFragmentViewModel::class)
    abstract fun bind(viewModel: MixerFragmentViewModel) : ViewModel
}