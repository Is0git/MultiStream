package com.android.multistream.di.main_activity.main_fragments.profile_fragments.twitch_profile_fragment.modules

import androidx.lifecycle.ViewModel
import com.android.multistream.di.main_activity.ViewModelKey
import com.android.multistream.di.main_activity.main_fragments.profile_fragments.twitch_profile_fragment.ProfileFragmentScope
import com.android.multistream.ui.main_activity.fragments.profile_fragment.twitch_profile.TwitchProfileViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class TwitchProfileViewModelModule {

    @ProfileFragmentScope
    @IntoMap
    @Binds
    @ViewModelKey(TwitchProfileViewModel::class)
    abstract fun getTwitchProfileViewModel(viewModel: TwitchProfileViewModel) : ViewModel
}