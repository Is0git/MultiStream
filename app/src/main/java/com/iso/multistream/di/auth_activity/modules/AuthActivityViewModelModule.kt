package com.iso.multistream.di.auth_activity.modules

import androidx.lifecycle.ViewModel
import com.iso.multistream.di.auth_activity.AuthScope
import com.iso.multistream.di.main_activity.ViewModelKey
import com.iso.multistream.ui.auth_activity.fragments.AuthViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class AuthActivityViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(AuthViewModel::class)
    @AuthScope
    abstract fun bindAuthViewModel(viewModel: AuthViewModel) : ViewModel
}