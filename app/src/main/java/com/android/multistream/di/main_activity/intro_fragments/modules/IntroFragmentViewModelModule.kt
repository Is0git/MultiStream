package com.android.multistream.di.main_activity.intro_fragments.modules

import androidx.lifecycle.ViewModel
import com.android.multistream.di.main_activity.ViewModelKey
import com.android.multistream.di.main_activity.intro_fragments.scopes.IntroFragmentScope
import com.android.multistream.di.main_activity.scopes.MainActivityScope
import com.android.multistream.ui.intro.fragments.IntroViewModel
import com.android.multistream.ui.main.activities.main_activity.MainActivityViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class IntroFragmentViewModelModule {

    @Binds
    @IntoMap
    @IntroFragmentScope
    @ViewModelKey(IntroViewModel::class)
    abstract fun introViewModel(viewModel: IntroViewModel) : ViewModel
}