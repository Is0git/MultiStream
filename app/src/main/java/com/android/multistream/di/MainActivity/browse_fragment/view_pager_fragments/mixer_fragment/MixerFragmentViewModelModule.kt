package com.android.multistream.di.MainActivity.browse_fragment.view_pager_fragments.mixer_fragment

import androidx.lifecycle.ViewModel
import com.android.multistream.di.MainActivity.ViewModelKey
import com.android.multistream.ui.browse_fragment.view_pager_fragments.mixer_fragment.MixerFragmentViewModel
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