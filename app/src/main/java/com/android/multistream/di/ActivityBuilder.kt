package com.android.multistream.di

import com.android.multistream.di.MainActivity.FragmentBuilder
import com.android.multistream.ui.activities.main_activity.MainActivity
import com.android.multistream.di.MainActivity.MainActivityScope
import com.android.multistream.di.MainActivity.MainActivityViewModelModule
import com.android.multistream.di.MainActivity.ViewModelFactoryModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilder {
    @MainActivityScope
    @ContributesAndroidInjector(modules = [ViewModelFactoryModule::class, MainActivityViewModelModule::class, FragmentBuilder::class])
    abstract fun mainActivity() : MainActivity

}