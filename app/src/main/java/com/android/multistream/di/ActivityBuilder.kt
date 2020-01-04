package com.android.multistream.di

import com.android.multistream.ui.MainActivity
import com.android.multistream.di.MainActivity.MainActivityScope
import com.android.multistream.di.MainActivity.MainActivityViewModelModule
import com.android.multistream.di.MainActivity.ViewModelFactoryModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilder {
    @MainActivityScope
    @ContributesAndroidInjector(modules = [ViewModelFactoryModule::class, MainActivityViewModelModule::class])
    abstract fun mainActivity() : MainActivity

}