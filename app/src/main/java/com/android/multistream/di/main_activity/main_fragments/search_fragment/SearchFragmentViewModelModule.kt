package com.android.multistream.di.main_activity.main_fragments.search_fragment

import androidx.lifecycle.ViewModel
import com.android.multistream.di.main_activity.ViewModelKey
import com.android.multistream.ui.main.fragments.search_fragment.SearchViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class SearchFragmentViewModelModule {
    @Binds
    @IntoMap
    @SearchFragmentScope
    @ViewModelKey(SearchViewModel::class)
    abstract fun getSearchViewModel(searchViewModel: SearchViewModel) : ViewModel

}