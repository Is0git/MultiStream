package com.android.multistream.di.MainActivity

import androidx.lifecycle.ViewModel
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
    abstract fun viewModel(viewModel: MainActivityViewModel) : ViewModel
}