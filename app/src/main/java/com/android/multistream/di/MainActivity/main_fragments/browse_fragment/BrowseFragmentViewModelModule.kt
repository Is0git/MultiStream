package com.android.multistream.di.MainActivity.main_fragments.browse_fragment

import androidx.lifecycle.ViewModel
import com.android.multistream.di.MainActivity.ViewModelKey
import com.android.multistream.ui.main.fragments.browse_fragment.BrowseFragmentViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class BrowseFragmentViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(BrowseFragmentViewModel::class)
    @BrowseFragmentScope
    abstract fun browseViewModel(browseFragmentViewModel: BrowseFragmentViewModel) : ViewModel
}