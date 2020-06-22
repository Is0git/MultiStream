package com.iso.multistream.di.main_activity.main_fragments.profile_fragments.twitch_profile_fragment.twitch_profile_fragments

import androidx.lifecycle.ViewModel
import com.iso.multistream.di.main_activity.ViewModelKey
import com.iso.multistream.di.main_activity.main_fragments.profile_fragments.twitch_profile_fragment.ProfileFragmentScope
import com.iso.multistream.ui.main_activity.fragments.profile_fragment.twitch_profile.TwitchProfileViewModel
import com.iso.multistream.ui.main_activity.fragments.profile_fragment.twitch_profile.past_recordings.TwitchPastStreamsViewModel
import com.iso.multistream.ui.main_activity.fragments.profile_fragment.twitch_profile.past_videos.TwitchPastVideosViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class TwitchProfileFragmentViewModelModule  {

    @Binds
    @IntoMap
    @ProfileFragmentScope
    @ViewModelKey(TwitchPastStreamsViewModel::class)
    abstract fun getPastVideosViewModel(viewModel: TwitchPastStreamsViewModel) : ViewModel

    @Binds
    @IntoMap
    @ProfileFragmentScope
    @ViewModelKey(TwitchPastVideosViewModel::class)
    abstract fun getPastClipsViewModel(viewModel: TwitchPastVideosViewModel) : ViewModel

    @ProfileFragmentScope
    @IntoMap
    @Binds
    @ViewModelKey(TwitchProfileViewModel::class)
    abstract fun getTwitchProfileViewModel(viewModel: TwitchProfileViewModel) : ViewModel
}