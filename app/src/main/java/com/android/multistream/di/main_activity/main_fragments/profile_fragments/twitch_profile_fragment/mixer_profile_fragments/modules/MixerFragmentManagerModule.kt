package com.android.multistream.di.main_activity.main_fragments.profile_fragments.twitch_profile_fragment.mixer_profile_fragments.modules

import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import com.android.multistream.di.main_activity.main_fragments.profile_fragments.twitch_profile_fragment.ProfileFragmentScope
import com.android.multistream.ui.main_activity.fragments.profile_fragment.mixer_profile.MixerProfileFragment
import dagger.Module
import dagger.Provides
@Module
object MixerFragmentManagerModule {

        @Provides
        @JvmStatic
        @ProfileFragmentScope
        fun getChildFragmentManager(profile: MixerProfileFragment) : FragmentManager {
            return  profile.childFragmentManager
        }

        @Provides
        @JvmStatic
        @ProfileFragmentScope
        fun getProfileLifecycle(profile: MixerProfileFragment) : Lifecycle {
            return profile.lifecycle
        }
}