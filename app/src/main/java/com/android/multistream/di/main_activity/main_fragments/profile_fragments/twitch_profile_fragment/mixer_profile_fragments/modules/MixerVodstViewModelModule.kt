package com.android.multistream.di.main_activity.main_fragments.profile_fragments.twitch_profile_fragment.mixer_profile_fragments.modules

import androidx.lifecycle.ViewModel
import com.android.multistream.di.main_activity.ViewModelKey
import com.android.multistream.di.main_activity.main_fragments.profile_fragments.twitch_profile_fragment.ProfileFragmentScope
import com.android.multistream.ui.main_activity.fragments.profile_fragment.mixer_profile.vods.MixerVodsViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class MixerVodstViewModelModule {

    @Binds
    @IntoMap
    @ProfileFragmentScope
    @ViewModelKey(MixerVodsViewModel::class)
    abstract fun getMixerVodsViewModel(viewModel: MixerVodsViewModel) : ViewModel
}