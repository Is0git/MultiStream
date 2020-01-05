package com.android.multistream.di.MainActivity

import com.android.multistream.di.MainActivity.home_fragment.HomeFragmentScope
import com.android.multistream.di.MainActivity.home_fragment.HomeFragmentViewModelModule
import com.android.multistream.ui.home_fragment.HomeFragment
import com.android.multistream.ui.home_fragment.HomeFragmentViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentBuilder {

    @ContributesAndroidInjector(modules = [HomeFragmentViewModelModule::class])
    @HomeFragmentScope
    abstract fun homeFragment() : HomeFragment
}