package com.iso.multistream.di.main_activity.player_fragment.modules

import androidx.lifecycle.ViewModel
import com.iso.multistream.di.main_activity.ViewModelKey
import com.iso.multistream.di.main_activity.player_fragment.PlayerFragmentScope
import com.iso.multistream.ui.player.fragments.vod_player_fragment.VodPlayerViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class VodStreamPlayerViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(VodPlayerViewModel::class)
    @PlayerFragmentScope
    abstract fun getVodPlayerViewModel(viewModel: VodPlayerViewModel) : ViewModel
}