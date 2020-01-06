package com.android.multistream.ui.home_fragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.multistream.di.MainActivity.home_fragment.HomeFragmentScope
import kotlinx.coroutines.launch
import javax.inject.Inject
@HomeFragmentScope
class HomeFragmentViewModel @Inject constructor(val repo: HomeFragmentRepository) : ViewModel() {


}