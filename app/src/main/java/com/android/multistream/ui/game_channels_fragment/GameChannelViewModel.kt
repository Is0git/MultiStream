package com.android.multistream.ui.game_channels_fragment

import androidx.lifecycle.ViewModel
import com.android.multistream.di.MainActivity.game_channels_fragment.GameChannelsFragmentScope
import javax.inject.Inject

@GameChannelsFragmentScope
class GameChannelViewModel @Inject constructor(val repo: GameChannelsRepository) : ViewModel() {
    val pageLoadLiveData = repo.keyPager?.dataLiveData

    fun getLoadState() = repo.keyPager?.pageLoadingState?.value

    fun loadNextPage() = repo.keyPager?.loadHandler()

    fun setGame(id: String?) {repo.gameId = id ?: "null"}


    fun initPager() {
        repo.initKeyPager()
    }

}