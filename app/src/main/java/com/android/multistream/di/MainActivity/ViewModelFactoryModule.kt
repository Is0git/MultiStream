package com.android.multistream.di.MainActivity

import androidx.lifecycle.ViewModelProvider
import com.android.multistream.utils.ViewModelFactory
import dagger.Binds
import dagger.Module

@Module
abstract class ViewModelFactoryModule {
    @Binds
    @MainActivityScope
    abstract fun viewModelFactory(viewModelFactory: ViewModelFactory): ViewModelProvider.Factory
}