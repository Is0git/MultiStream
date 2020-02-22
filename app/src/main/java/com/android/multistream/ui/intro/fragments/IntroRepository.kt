package com.android.multistream.ui.intro.fragments

import android.content.pm.PackageManager
import com.android.multistream.di.main_activity.intro_fragments.scopes.IntroFragmentScope
import javax.inject.Inject

@IntroFragmentScope
class IntroRepository @Inject constructor(val packageManager: PackageManager) {
}