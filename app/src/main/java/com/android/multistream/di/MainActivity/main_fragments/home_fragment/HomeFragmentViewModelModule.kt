package com.android.multistream.di.MainActivity.main_fragments.home_fragment

import androidx.lifecycle.ViewModel
import com.android.multistream.di.MainActivity.ViewModelKey
import com.android.multistream.ui.main.fragments.home_fragment.view_model.HomeFragmentViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class HomeFragmentViewModelModule
{
    @Binds
    @IntoMap
    @HomeFragmentScope
    @ViewModelKey(HomeFragmentViewModel::class)
    abstract fun homeFragment(viewModel: HomeFragmentViewModel): ViewModel
}