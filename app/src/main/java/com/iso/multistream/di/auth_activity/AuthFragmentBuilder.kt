package com.iso.multistream.di.auth_activity
import com.iso.multistream.ui.auth_activity.fragments.intro.IntroPageFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class AuthFragmentBuilder {

    @ContributesAndroidInjector
    abstract fun introFragmentTwo(): IntroPageFragment
}