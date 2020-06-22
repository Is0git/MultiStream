package com.iso.multistream.di.main_activity.main_fragments.search_fragment

import androidx.lifecycle.ViewModel
import com.iso.multistream.di.main_activity.ViewModelKey
import com.iso.multistream.ui.main_activity.fragments.search_fragment.SearchViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class SearchFragmentViewModelModule {
    @Binds
    @IntoMap
    @SearchFragmentScope
    @ViewModelKey(SearchViewModel::class)
    abstract fun getSearchViewModel(searchViewModel: SearchViewModel): ViewModel

}