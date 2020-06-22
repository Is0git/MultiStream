package com.iso.multistream.di.main_activity.modules

import androidx.lifecycle.ViewModelProvider
import com.example.daggerviewmodelfragment.ViewModelFactory
import com.iso.multistream.di.main_activity.MainActivityScope
import dagger.Binds
import dagger.Module

@Module
abstract class ViewModelFactoryModule {
    @Binds
    @MainActivityScope
    abstract fun viewModelFactory(viewModelFactory: ViewModelFactory): ViewModelProvider.Factory
}