package com.iso.multistream.di.main_activity.modules

import androidx.lifecycle.ViewModel
import com.iso.multistream.di.main_activity.MainActivityScope
import com.iso.multistream.di.main_activity.ViewModelKey
import com.iso.multistream.ui.main_activity.MainActivityViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class MainActivityViewModelModule {
    @Binds
    @IntoMap
    @MainActivityScope
    @ViewModelKey(MainActivityViewModel::class)
    abstract fun viewModel(viewModel: MainActivityViewModel): ViewModel
}