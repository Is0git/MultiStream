package com.android.multistream.di

import com.android.multistream.di.main_activity.FragmentBuilder
import com.android.multistream.di.main_activity.modules.MainActivityViewModelModule
import com.android.multistream.di.main_activity.modules.ViewModelFactoryModule
import com.android.multistream.di.main_activity.scopes.MainActivityScope
import com.android.multistream.di.modules.SettingsModule
import com.android.multistream.ui.main_activity.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilder {
    @MainActivityScope
    @ContributesAndroidInjector(modules = [ViewModelFactoryModule::class, MainActivityViewModelModule::class, FragmentBuilder::class, SettingsModule::class])
    abstract fun mainActivity(): MainActivity

}