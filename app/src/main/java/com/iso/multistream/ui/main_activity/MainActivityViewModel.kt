package com.iso.multistream.ui.main_activity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iso.multistream.auth.platforms.Platform
import com.iso.multistream.di.main_activity.MainActivityScope
import com.iso.multistream.network.twitch.models.v5.current_user.CurrentUser
import kotlinx.coroutines.launch
import javax.inject.Inject

@MainActivityScope
class MainActivityViewModel @Inject constructor(val repo: MainActivityRepository) : ViewModel() {

    val twitchFollowingChannelsPageLoader = repo.pageLoader

    fun isValidated(clazz: Class<out Platform<*, *, *, *>>): Boolean {
        return repo.isValidated(clazz)
    }

    fun getToken(clazz: Class<out Platform<*, *, *, *>>): String? {
        return repo.getToken(clazz)
    }

    fun getTwitchUser(): CurrentUser? {
        return repo.getTwitchUser()
    }


    fun followTwitchUser(channelId: String) {
        viewModelScope.launch {
            repo.followTwitchUser(channelId)
        }
    }

    fun unFollowTwitchUser(channelId: String) {
        viewModelScope.launch {
            repo.unFollowTwitchUser(channelId)
        }
    }

}