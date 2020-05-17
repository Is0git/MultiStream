package com.android.multistream.di.main_activity.modules

import androidx.lifecycle.ViewModelProvider
import com.android.multistream.di.main_activity.scopes.MainActivityScope
import com.example.daggerviewmodelfragment.ViewModelFactory
import dagger.Binds
import dagger.Module

@Module
abstract class ViewModelFactoryModule {
    @Binds
    @MainActivityScope
    abstract fun viewModelFactory(viewModelFactory: ViewModelFactory): ViewModelProvider.Factory
}