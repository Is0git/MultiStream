package com.android.multistream.di.MainActivity

import com.android.multistream.di.MainActivity.intro_fragments.IntroFragmentScope.IntroFragmentScope
import com.android.multistream.di.MainActivity.main_fragments.browse_fragment.BrowseFragmentScope
import com.android.multistream.di.MainActivity.main_fragments.browse_fragment.BrowseFragmentViewModelModule
import com.android.multistream.di.MainActivity.main_fragments.browse_fragment.ViewPagerFragmentBuilder
import com.android.multistream.di.MainActivity.main_fragments.combined_channels_fragment.CombinedChannelsScope
import com.android.multistream.di.MainActivity.main_fragments.combined_channels_fragment.CombinedChannelsViewModelModule
import com.android.multistream.di.MainActivity.main_fragments.game_channels_fragment.GameChannelsFragmentScope
import com.android.multistream.di.MainActivity.main_fragments.game_channels_fragment.GameChannelsViewModelModule
import com.android.multistream.di.MainActivity.main_fragments.home_fragment.HomeFragmentScope
import com.android.multistream.di.MainActivity.main_fragments.home_fragment.HomeFragmentViewModelModule
import com.android.multistream.ui.intro.fragments.IntroPage
import com.android.multistream.ui.main.fragments.browse_fragment.BrowseFragment
import com.android.multistream.ui.main.fragments.combined_games_channels_fragment.CombinedChannelsFragment
import com.android.multistream.ui.main.fragments.game_channels_fragment.GameChannelsFragment
import com.android.multistream.ui.main.fragments.home_fragment.HomeFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentBuilder {

    @ContributesAndroidInjector(modules = [HomeFragmentViewModelModule::class])
    @HomeFragmentScope
    abstract fun homeFragment() : HomeFragment

    @ContributesAndroidInjector(modules = [BrowseFragmentViewModelModule::class, ViewPagerFragmentBuilder::class])
    @BrowseFragmentScope
    abstract fun browseFragment() : BrowseFragment

    @ContributesAndroidInjector(modules = [GameChannelsViewModelModule::class])
    @GameChannelsFragmentScope
    abstract fun gameChannelsFragment() : GameChannelsFragment

    @ContributesAndroidInjector(modules = [CombinedChannelsViewModelModule::class])
    @CombinedChannelsScope
    abstract fun combinedChannelsFragment() : CombinedChannelsFragment

    @ContributesAndroidInjector
    @IntroFragmentScope
    abstract fun introFragment() : IntroPage
}