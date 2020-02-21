package com.android.multistream.ui.main.activities.main_activity

import androidx.lifecycle.ViewModel
import com.android.multistream.di.main_activity.scopes.MainActivityScope
import javax.inject.Inject
@MainActivityScope
class MainActivityViewModel @Inject constructor(val repo: MainActivityRepository) : ViewModel() {

//    fun authorize(type: String, token: String) {
//        viewModelScope.launch { repo.authorize(type, token) }
//    }
//
//    fun getToken(type: String) = repo.getToken(type)
//

}