package com.android.multistream.ui.main.activities.main_activity

import android.net.Uri
import androidx.lifecycle.ViewModel
import com.android.multistream.di.main_activity.scopes.MainActivityScope
import javax.inject.Inject
@MainActivityScope
class MainActivityViewModel @Inject constructor(val repo: MainActivityRepository) : ViewModel() {

    val statesLiveData = repo.twitchPlatformAuthLiveData

    fun getAndSaveToken(code: Uri?) {
        repo.getAndSaveToken(code)
    }


}