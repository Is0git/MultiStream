package com.iso.multistream.di.main_activity.main_fragments.home_fragment

import androidx.lifecycle.ViewModel
import com.iso.multistream.di.main_activity.ViewModelKey
import com.iso.multistream.ui.main_activity.fragments.home_fragment.view_model.HomeFragmentViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class HomeFragmentViewModelModule {
    @Binds
    @IntoMap
    @HomeFragmentScope
    @ViewModelKey(HomeFragmentViewModel::class)
    abstract fun homeFragment(viewModel: HomeFragmentViewModel): ViewModel
}