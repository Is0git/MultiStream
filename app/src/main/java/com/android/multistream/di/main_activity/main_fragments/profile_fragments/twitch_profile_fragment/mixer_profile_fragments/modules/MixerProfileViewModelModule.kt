package com.android.multistream.di.main_activity.main_fragments.profile_fragments.twitch_profile_fragment.mixer_profile_fragments.modules

import androidx.lifecycle.ViewModel
import com.android.multistream.di.main_activity.ViewModelKey
import com.android.multistream.di.main_activity.main_fragments.profile_fragments.twitch_profile_fragment.ProfileFragmentScope
import com.android.multistream.ui.main.fragments.profile_fragment.mixer_profile.MixerProfileViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class MixerProfileViewModelModule {

    @IntoMap
    @Binds
    @ViewModelKey(MixerProfileViewModel::class)
    @ProfileFragmentScope
    abstract fun getMixerProfileViewModel(viewModel: MixerProfileViewModel) : ViewModel
}