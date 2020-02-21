package com.android.multistream.di.main_activity.main_fragments.browse_fragment.view_pager_fragments.top_fragment

import androidx.lifecycle.ViewModel
import com.android.multistream.di.main_activity.ViewModelKey
import com.android.multistream.ui.main.fragments.browse_fragment.view_pager_fragments.top_fragment.TopFragmentViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class TopFragmentViewModelModule {
    @Binds
    @IntoMap
    @TopFragmentGamesScope
    @ViewModelKey(TopFragmentViewModel::class)
    abstract fun bind(viewModel: TopFragmentViewModel) : ViewModel
}