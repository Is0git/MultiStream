package com.android.multistream.ui.intro.fragments

import androidx.lifecycle.ViewModel
import com.android.multistream.di.main_activity.intro_fragments.scopes.IntroFragmentScope
import javax.inject.Inject

@IntroFragmentScope
class IntroViewModel @Inject constructor(val repo: IntroRepository) : ViewModel()