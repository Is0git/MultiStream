package com.android.multistream.di

import com.android.multistream.di.auth_activity.AuthFragmentBuilder
import com.android.multistream.di.auth_activity.AuthScope
import com.android.multistream.di.auth_activity.modules.AuthActivityViewModelModule
import com.android.multistream.di.main_activity.MainFragmentBuilder
import com.android.multistream.di.main_activity.modules.MainActivityViewModelModule
import com.android.multistream.di.main_activity.modules.ViewModelFactoryModule
import com.android.multistream.di.main_activity.MainActivityScope
import com.android.multistream.di.modules.SettingsModule
import com.android.multistream.ui.auth_activity.fragments.AuthActivity
import com.android.multistream.ui.main_activity.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilder {
    @MainActivityScope
    @ContributesAndroidInjector(modules = [ViewModelFactoryModule::class, MainActivityViewModelModule::class, MainFragmentBuilder::class])
    abstract fun mainActivity(): MainActivity

    @AuthScope
    @ContributesAndroidInjector(modules = [AuthFragmentBuilder::class, AuthActivityViewModelModule::class])
    abstract fun authActivity() : AuthActivity

}