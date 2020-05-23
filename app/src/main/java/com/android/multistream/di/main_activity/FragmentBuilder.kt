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
import com.android.multistream.di.main_activity.main_fragments.profile_fragments.twitch_profile_fragment.twitch_profile_fragments.modules.TwitchFragmentManagerModule
import com.android.multistream.di.main_activity.main_fragments.profile_fragments.twitch_profile_fragment.ProfileFragmentScope
import com.android.multistream.di.main_activity.main_fragments.profile_fragments.twitch_profile_fragment.mixer_profile_fragments.modules.MixerVodstViewModelModule
import com.android.multistream.di.main_activity.main_fragments.profile_fragments.twitch_profile_fragment.mixer_profile_fragments.modules.MixerFragmentManagerModule
import com.android.multistream.di.main_activity.main_fragments.profile_fragments.twitch_profile_fragment.mixer_profile_fragments.modules.MixerProfileViewModelModule
import com.android.multistream.di.main_activity.main_fragments.profile_fragments.twitch_profile_fragment.twitch_profile_fragments.TwitchProfileFragmentViewModelModule
import com.android.multistream.di.main_activity.main_fragments.search_fragment.SearchFragmentScope
import com.android.multistream.di.main_activity.main_fragments.search_fragment.SearchFragmentViewModelModule
import com.android.multistream.di.main_activity.main_fragments.view_all_fragments.ViewAllFragmentScope
import com.android.multistream.di.main_activity.main_fragments.view_all_fragments.twitch_view_all_fragments.channels.TwitchChannelsViewAllViewModelModule
import com.android.multistream.di.main_activity.main_fragments.view_all_fragments.twitch_view_all_fragments.games.TwitchGamesViewAllViewModelModule
import com.android.multistream.di.main_activity.main_fragments.view_all_fragments.twitch_view_all_fragments.streams.TwitchStreamsViewAllViewModelModule
import com.android.multistream.ui.intro.fragments.IntroPage
import com.android.multistream.ui.intro.fragments.IntroPageTwo
import com.android.multistream.ui.main.fragments.browse_fragment.BrowseFragment
import com.android.multistream.ui.main.fragments.game_category_fragment.mixer_game_category.MixerGameCategory
import com.android.multistream.ui.main.fragments.game_category_fragment.twitch_game_category.TwitchGameCategory
import com.android.multistream.ui.main.fragments.home_fragment.HomeFragment
import com.android.multistream.ui.main.fragments.profile_fragment.mixer_profile.MixerProfileFragment
import com.android.multistream.ui.main.fragments.profile_fragment.mixer_profile.vods.MixerVodsFragment
import com.android.multistream.ui.main.fragments.profile_fragment.twitch_profile.TwitchProfileFragment
import com.android.multistream.ui.main.fragments.profile_fragment.twitch_profile.past_recordings.TwitchPastStreamsFragment
import com.android.multistream.ui.main.fragments.profile_fragment.twitch_profile.past_videos.TwitchClipsFragment
import com.android.multistream.ui.main.fragments.search_fragment.SearchFragment
import com.android.multistream.ui.main.fragments.view_all_fragments.twitch.channels_view_all.TwitchChannelsAllViewFragment
import com.android.multistream.ui.main.fragments.view_all_fragments.twitch.games_view_all.TwitchGamesViewAllFragment
import com.android.multistream.ui.main.fragments.view_all_fragments.twitch.streams_view_all.TwitchStreamsAllViewFragment
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

    @ContributesAndroidInjector(modules = [TwitchProfileFragmentViewModelModule::class])
    @ProfileFragmentScope
    abstract fun twitchVideosFragment() : TwitchPastStreamsFragment

    @ContributesAndroidInjector(modules = [TwitchProfileFragmentViewModelModule::class])
    @ProfileFragmentScope
    abstract fun twitchClipsFragment() : TwitchClipsFragment

    @ContributesAndroidInjector(modules = [MixerVodstViewModelModule::class])
    @ProfileFragmentScope
    abstract fun mixerVodsFragment() : MixerVodsFragment

    @ContributesAndroidInjector(modules = [TwitchGamesViewAllViewModelModule::class])
    @ViewAllFragmentScope
    abstract fun getTwitchGamesViewAll() : TwitchGamesViewAllFragment

    @ContributesAndroidInjector(modules = [TwitchStreamsViewAllViewModelModule::class])
    @ViewAllFragmentScope
    abstract fun getTwitchStreamsViewAll() : TwitchStreamsAllViewFragment

    @ContributesAndroidInjector(modules = [TwitchChannelsViewAllViewModelModule::class])
    @ViewAllFragmentScope
    abstract fun getTwitchChannelsViewAll() : TwitchChannelsAllViewFragment

    @ContributesAndroidInjector(modules = [TwitchProfileFragmentViewModelModule::class, TwitchFragmentManagerModule::class])
    @ProfileFragmentScope
    abstract fun getTwitchProfile() : TwitchProfileFragment

    @ContributesAndroidInjector(modules = [MixerProfileViewModelModule::class, MixerFragmentManagerModule::class])
    @ProfileFragmentScope
    abstract fun getMixerProfile() : MixerProfileFragment
}