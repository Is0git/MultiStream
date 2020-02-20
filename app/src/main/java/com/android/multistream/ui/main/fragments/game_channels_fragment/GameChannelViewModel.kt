package com.android.multistream.ui.main.fragments.game_channels_fragment

import androidx.lifecycle.ViewModel
import com.android.multistream.di.MainActivity.main_fragments.game_channels_fragment.GameChannelsFragmentScope
import com.android.multistream.utils.TWITCH
import javax.inject.Inject

@GameChannelsFragmentScope
class GameChannelViewModel @Inject constructor(val repo: GameChannelsRepository) : ViewModel() {


    fun getPagedLoadData() = repo.keyPager?.dataLiveData

    fun getMixerPagedLoadData() = repo.pagedPositionLoader.dataLiveData



    fun getLoadState(type: Int) = if(type == TWITCH) repo.keyPager?.pageLoadingState?.value else repo.pagedPositionLoader.pageLoadingState.value

    fun loadNextPage(type: Int) = if(type == TWITCH) repo.keyPager?.loadHandler() else repo.pagedPositionLoader.loadHandler()

    fun setGame(id: String?) {repo.gameId = id ?: "null"}



}