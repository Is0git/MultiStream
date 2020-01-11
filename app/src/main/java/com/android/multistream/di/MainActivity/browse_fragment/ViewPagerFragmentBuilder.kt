package com.android.multistream.di.MainActivity.browse_fragment

import com.android.multistream.di.MainActivity.browse_fragment.view_pager_fragments.mixer_fragment.MixerFragmentGamesScope
import com.android.multistream.di.MainActivity.browse_fragment.view_pager_fragments.top_fragment.TopFragmentGamesScope
import com.android.multistream.di.MainActivity.browse_fragment.view_pager_fragments.twitch_fragment.TwitchFragmentGamesScope
import com.android.multistream.ui.browse_fragment.view_pager_fragments.mixer_fragment.MixerFragment
import com.android.multistream.ui.browse_fragment.view_pager_fragments.top_fragment.TopGamesFragment
import com.android.multistream.ui.browse_fragment.view_pager_fragments.twitch_fragment.TwitchFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ViewPagerFragmentBuilder  {

    @ContributesAndroidInjector
    @TwitchFragmentGamesScope
    abstract fun twitchFragment() : TwitchFragment

    @ContributesAndroidInjector
    @TopFragmentGamesScope
    abstract fun topFragment() : TopGamesFragment

    @ContributesAndroidInjector
    @MixerFragmentGamesScope
    abstract fun mixerFragment() : MixerFragment
}