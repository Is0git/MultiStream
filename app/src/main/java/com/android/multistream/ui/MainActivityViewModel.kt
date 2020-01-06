package com.android.multistream.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.multistream.di.MainActivity.MainActivityScope
import kotlinx.coroutines.launch
import javax.inject.Inject
@MainActivityScope
class MainActivityViewModel @Inject constructor(val repo: MainActivityRepository) : ViewModel() {

    fun authorize(type: String, token: String) {
        viewModelScope.launch { repo.authorize(type, token) }
    }

    fun getToken(type: String) = repo.getToken(type)


}