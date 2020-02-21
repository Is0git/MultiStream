package com.android.multistream.di

import com.android.multistream.di.main_activity.FragmentBuilder
import com.android.multistream.ui.main.activities.main_activity.MainActivity
import com.android.multistream.di.main_activity.scopes.MainActivityScope
import com.android.multistream.di.main_activity.modules.MainActivityViewModelModule
import com.android.multistream.di.main_activity.modules.ViewModelFactoryModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilder {
    @MainActivityScope
    @ContributesAndroidInjector(modules = [ViewModelFactoryModule::class, MainActivityViewModelModule::class, FragmentBuilder::class])
    abstract fun mainActivity() : MainActivity

}