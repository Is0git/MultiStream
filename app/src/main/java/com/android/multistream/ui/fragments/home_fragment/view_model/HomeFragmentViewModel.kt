package com.android.multistream.ui.fragments.home_fragment.view_model

import androidx.lifecycle.ViewModel
import com.android.multistream.di.MainActivity.home_fragment.HomeFragmentScope
import com.android.multistream.ui.fragments.home_fragment.view_model.HomeFragmentRepository
import javax.inject.Inject
@HomeFragmentScope
class HomeFragmentViewModel @Inject constructor(val repo: HomeFragmentRepository) : ViewModel() {


}