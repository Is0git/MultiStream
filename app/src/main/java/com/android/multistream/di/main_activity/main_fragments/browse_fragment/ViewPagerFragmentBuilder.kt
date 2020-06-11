package com.android.multistream.di.main_activity.main_fragments.browse_fragment

import com.android.multistream.di.main_activity.main_fragments.browse_fragment.view_pager_fragments.mixer_fragment.MixerFragmentViewModelModule
import com.android.multistream.di.main_activity.main_fragments.browse_fragment.view_pager_fragments.mixer_fragment.MixerGamesBrowseFragmentScope
import com.android.multistream.di.main_activity.main_fragments.browse_fragment.view_pager_fragments.twitch_fragment.TwitchGamesBrowseFragmentScope
import com.android.multistream.ui.main_activity.fragments.browse_fragment.view_pager_fragments.mixer_fragment.MixerGamesBrowseFragment
import com.android.multistream.ui.main_activity.fragments.browse_fragment.view_pager_fragments.twitch_fragment.TwitchGamesBrowseFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ViewPagerFragmentBuilder {

    @ContributesAndroidInjector(modules = [TwitchGamesBrowseViewModelModule::class])
    @TwitchGamesBrowseFragmentScope
    abstract fun twitchFragment(): TwitchGamesBrowseFragment


    @ContributesAndroidInjector(modules = [MixerFragmentViewModelModule::class])
    @MixerGamesBrowseFragmentScope
    abstract fun mixerFragment(): MixerGamesBrowseFragment
}