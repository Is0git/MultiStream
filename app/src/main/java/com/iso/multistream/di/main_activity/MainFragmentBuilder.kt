package com.iso.multistream.di.main_activity

import com.iso.multistream.di.main_activity.main_fragments.browse_fragment.BrowseFragmentScope
import com.iso.multistream.di.main_activity.main_fragments.browse_fragment.ViewPagerFragmentBuilder
import com.iso.multistream.di.main_activity.main_fragments.game_category_fragment.mixer_game_category_fragment.MixerGameCategoryScope
import com.iso.multistream.di.main_activity.main_fragments.game_category_fragment.mixer_game_category_fragment.MixerGameCategoryViewModelModule
import com.iso.multistream.di.main_activity.main_fragments.game_category_fragment.twitch_game_category_fragment.TwitchGameCategoryScope
import com.iso.multistream.di.main_activity.main_fragments.game_category_fragment.twitch_game_category_fragment.TwitchGameCategoryViewModelModule
import com.iso.multistream.di.main_activity.main_fragments.home_fragment.HomeFragmentScope
import com.iso.multistream.di.main_activity.main_fragments.home_fragment.HomeFragmentViewModelModule
import com.iso.multistream.di.main_activity.main_fragments.profile_fragments.twitch_profile_fragment.ProfileFragmentScope
import com.iso.multistream.di.main_activity.main_fragments.profile_fragments.twitch_profile_fragment.mixer_profile_fragments.modules.MixerFragmentManagerModule
import com.iso.multistream.di.main_activity.main_fragments.profile_fragments.twitch_profile_fragment.mixer_profile_fragments.modules.MixerProfileViewModelModule
import com.iso.multistream.di.main_activity.main_fragments.profile_fragments.twitch_profile_fragment.mixer_profile_fragments.modules.MixerVodstViewModelModule
import com.iso.multistream.di.main_activity.main_fragments.profile_fragments.twitch_profile_fragment.twitch_profile_fragments.TwitchProfileFragmentViewModelModule
import com.iso.multistream.di.main_activity.main_fragments.profile_fragments.twitch_profile_fragment.twitch_profile_fragments.modules.TwitchFragmentManagerModule
import com.iso.multistream.di.main_activity.main_fragments.search_fragment.SearchFragmentScope
import com.iso.multistream.di.main_activity.main_fragments.search_fragment.SearchFragmentViewModelModule
import com.iso.multistream.di.main_activity.main_fragments.settings_fragment.SettingsFragmentScope
import com.iso.multistream.di.main_activity.main_fragments.view_all_fragments.ViewAllFragmentScope
import com.iso.multistream.di.main_activity.main_fragments.view_all_fragments.mixer_view_all_fragments.channels.MixerTopChannelsViewAllViewModelModule
import com.iso.multistream.di.main_activity.main_fragments.view_all_fragments.mixer_view_all_fragments.games.MixerTopGamesViewAllViewModelModule
import com.iso.multistream.di.main_activity.main_fragments.view_all_fragments.twitch_view_all_fragments.channels.TwitchChannelsViewAllViewModelModule
import com.iso.multistream.di.main_activity.main_fragments.view_all_fragments.twitch_view_all_fragments.games.TwitchGamesViewAllViewModelModule
import com.iso.multistream.di.main_activity.main_fragments.view_all_fragments.twitch_view_all_fragments.streams.TwitchStreamsViewAllViewModelModule
import com.iso.multistream.di.main_activity.player_fragment.PlayerFragmentScope
import com.iso.multistream.di.main_activity.player_fragment.modules.LiveStreamPlayerViewModelModule
import com.iso.multistream.di.main_activity.player_fragment.modules.VodStreamPlayerViewModelModule
import com.iso.multistream.ui.main_activity.fragments.browse_fragment.BrowseFragment
import com.iso.multistream.ui.main_activity.fragments.game_category_fragment.mixer_game_category.MixerGameCategory
import com.iso.multistream.ui.main_activity.fragments.game_category_fragment.twitch_game_category.TwitchGameCategory
import com.iso.multistream.ui.main_activity.fragments.home_fragment.HomeFragment
import com.iso.multistream.ui.main_activity.fragments.profile_fragment.mixer_profile.MixerProfileFragment
import com.iso.multistream.ui.main_activity.fragments.profile_fragment.mixer_profile.vods.MixerVodsFragment
import com.iso.multistream.ui.main_activity.fragments.profile_fragment.twitch_profile.TwitchProfileFragment
import com.iso.multistream.ui.main_activity.fragments.profile_fragment.twitch_profile.past_recordings.TwitchPastStreamsFragment
import com.iso.multistream.ui.main_activity.fragments.profile_fragment.twitch_profile.past_videos.TwitchClipsFragment
import com.iso.multistream.ui.main_activity.fragments.search_fragment.SearchFragment
import com.iso.multistream.ui.main_activity.fragments.settings_fragment.SettingsFragment
import com.iso.multistream.ui.main_activity.fragments.view_all_fragments.mixer.top_channels_view_all.MixerChannelsAllViewFragment
import com.iso.multistream.ui.main_activity.fragments.view_all_fragments.mixer.top_games_view_all.MixerTopGamesViewAllFragment
import com.iso.multistream.ui.main_activity.fragments.view_all_fragments.twitch.channels_view_all.TwitchChannelsAllViewFragment
import com.iso.multistream.ui.main_activity.fragments.view_all_fragments.twitch.games_view_all.TwitchGamesViewAllFragment
import com.iso.multistream.ui.main_activity.fragments.view_all_fragments.twitch.streams_view_all.TwitchStreamsAllViewFragment
import com.iso.multistream.ui.player.fragments.live_stream_player_fragment.LiveStreamPlayerFragment
import com.iso.multistream.ui.player.fragments.vod_player_fragment.VodPlayerFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class MainFragmentBuilder {

    @ContributesAndroidInjector(modules = [HomeFragmentViewModelModule::class])
    @HomeFragmentScope
    abstract fun homeFragment(): HomeFragment

    @ContributesAndroidInjector(modules = [ViewPagerFragmentBuilder::class])
    @BrowseFragmentScope
    abstract fun browseFragment(): BrowseFragment

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

    @ContributesAndroidInjector(modules = [MixerTopChannelsViewAllViewModelModule::class])
    @ViewAllFragmentScope
    abstract fun getMixerTopChannelsViewAll() : MixerChannelsAllViewFragment

    @ContributesAndroidInjector(modules = [MixerTopGamesViewAllViewModelModule::class])
    @ViewAllFragmentScope
    abstract fun getMixerTopGamesViewAll() : MixerTopGamesViewAllFragment

    @ContributesAndroidInjector
    @SettingsFragmentScope
    abstract fun getSettingsFragment() : SettingsFragment

    @ContributesAndroidInjector(modules = [LiveStreamPlayerViewModelModule::class])
    @PlayerFragmentScope
    abstract fun getLiveStreamPlayerViewModel() : LiveStreamPlayerFragment

    @ContributesAndroidInjector(modules = [VodStreamPlayerViewModelModule::class])
    @PlayerFragmentScope
    abstract fun getVodPlayerViewModel() : VodPlayerFragment

}