package com.android.multistream.di.main_activity.modules

import androidx.lifecycle.ViewModel
import com.android.multistream.di.main_activity.ViewModelKey
import com.android.multistream.di.main_activity.scopes.MainActivityScope
import com.android.multistream.ui.main.activities.main_activity.MainActivityViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class MainActivityViewModelModule {
    @Binds
    @IntoMap
    @MainActivityScope
    @ViewModelKey(MainActivityViewModel::class)
    abstract fun viewModel(viewModel: MainActivityViewModel): ViewModel
}