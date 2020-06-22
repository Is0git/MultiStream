package com.iso.multistream.di

import com.iso.multistream.di.auth_activity.AuthFragmentBuilder
import com.iso.multistream.di.auth_activity.AuthScope
import com.iso.multistream.di.auth_activity.modules.AuthActivityViewModelModule
import com.iso.multistream.di.main_activity.MainActivityScope
import com.iso.multistream.di.main_activity.MainFragmentBuilder
import com.iso.multistream.di.main_activity.modules.MainActivityViewModelModule
import com.iso.multistream.di.main_activity.modules.ViewModelFactoryModule
import com.iso.multistream.ui.auth_activity.fragments.AuthActivity
import com.iso.multistream.ui.main_activity.MainActivity
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