package com.android.multistream.di.main_activity

import com.android.multistream.di.main_activity.intro_fragments.modules.IntroFragmentViewModelModule
import com.android.multistream.di.main_activity.intro_fragments.scopes.IntroFragmentScope
import com.android.multistream.di.main_activity.main_fragments.browse_fragment.BrowseFragmentScope
import com.android.multistream.di.main_activity.main_fragments.browse_fragment.ViewPagerFragmentBuilder
import com.android.multistream.di.main_activity.main_fragments.game_category_fragment.mixer_game_category_fragment.MixerGameCategoryScope
import com.android.multistream.di.main_activity.main_fragments.game_category_fragment.mixer_game_category_fragment.MixerGameCategoryViewModelModule
import com.android.multistream.di.main_activity.main_fragments.game_category_fragment.twitch_game_category_fragment.TwitchGameCategoryScope
import com.android.multistream.di.main_activity.main_fragments.game_category_fragment.twitch_game_category_fragment.TwitchGameCategoryViewModelModule
import com.android.multistream.di.main_activity.main_fragments.home_fragment.HomeFragmentScope
import com.android.multistream.di.main_activity.main_fragments.home_fragment.HomeFragmentViewModelModule
import com.android.multistream.di.main_activity.main_fragments.search_fragment.SearchFragmentScope
import com.android.multistream.di.main_activity.main_fragments.search_fragment.SearchFragmentViewModelModule
import com.android.multistream.ui.intro.fragments.IntroPage
import com.android.multistream.ui.intro.fragments.IntroPageTwo
import com.android.multistream.ui.main.fragments.browse_fragment.BrowseFragment
import com.android.multistream.ui.main.fragments.game_category_fragment.mixer_game_category.MixerGameCategory
import com.android.multistream.ui.main.fragments.game_category_fragment.twitch_game_category.TwitchGameCategory
import com.android.multistream.ui.main.fragments.home_fragment.HomeFragment
import com.android.multistream.ui.main.fragments.search_fragment.SearchFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentBuilder {

    @ContributesAndroidInjector(modules = [HomeFragmentViewModelModule::class])
    @HomeFragmentScope
    abstract fun homeFragment(): HomeFragment

    @ContributesAndroidInjector(modules = [ViewPagerFragmentBuilder::class])
    @BrowseFragmentScope
    abstract fun browseFragment(): BrowseFragment

    @ContributesAndroidInjector(modules = [IntroFragmentViewModelModule::class])
    @IntroFragmentScope
    abstract fun introFragment(): IntroPage

    @ContributesAndroidInjector
    @IntroFragmentScope
    abstract fun introFragmentTwo(): IntroPageTwo

    @ContributesAndroidInjector(modules = [SearchFragmentViewModelModule::class])
    @SearchFragmentScope
    abstract fun searchFragment(): SearchFragment

    @ContributesAndroidInjector(modules = [TwitchGameCategoryViewModelModule::class])
    @TwitchGameCategoryScope
    abstract fun twitchGameCategoryFragment() : TwitchGameCategory

    @ContributesAndroidInjector(modules = [MixerGameCategoryViewModelModule::class])
    @MixerGameCategoryScope
    abstract fun mixerGameCategoryFragment() : MixerGameCategory
}