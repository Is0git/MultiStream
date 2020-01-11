package com.android.multistream.di.MainActivity

import com.android.multistream.di.MainActivity.browse_fragment.BrowseFragmentScope
import com.android.multistream.di.MainActivity.browse_fragment.BrowseFragmentViewModelModule
import com.android.multistream.di.MainActivity.game_channels_fragment.GameChannelsFragmentScope
import com.android.multistream.di.MainActivity.game_channels_fragment.GameChannelsViewModelModule
import com.android.multistream.di.MainActivity.home_fragment.HomeFragmentScope
import com.android.multistream.di.MainActivity.home_fragment.HomeFragmentViewModelModule
import com.android.multistream.ui.browse_fragment.BrowseFragment
import com.android.multistream.ui.game_channels_fragment.GameChannelsFragment
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

    @ContributesAndroidInjector(modules = [BrowseFragmentViewModelModule::class])
    @BrowseFragmentScope
    abstract fun browseFragment() : BrowseFragment

    @ContributesAndroidInjector(modules = [GameChannelsViewModelModule::class])
    @GameChannelsFragmentScope
    abstract fun gameChannelsFragment() : GameChannelsFragment
}