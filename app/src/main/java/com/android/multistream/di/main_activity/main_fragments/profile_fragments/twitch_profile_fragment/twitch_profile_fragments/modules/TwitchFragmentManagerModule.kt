package com.android.multistream.di.main_activity.main_fragments.profile_fragments.twitch_profile_fragment.twitch_profile_fragments.modules

import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import com.android.multistream.di.main_activity.main_fragments.profile_fragments.twitch_profile_fragment.ProfileFragmentScope
import com.android.multistream.ui.main.fragments.profile_fragment.twitch_profile.TwitchProfileFragment
import dagger.Module
import dagger.Provides

@Module
object TwitchFragmentManagerModule {
    @Provides
    @JvmStatic
    @ProfileFragmentScope
    fun getChildFragmentManager(profile: TwitchProfileFragment) : FragmentManager {
      return  profile.childFragmentManager
    }

    @Provides
    @JvmStatic
    @ProfileFragmentScope
    fun getProfileLifecycle(profile: TwitchProfileFragment) : Lifecycle {
        return profile.lifecycle
    }
}