package com.android.multistream.di.main_activity.main_fragments.view_all_fragments.mixer_view_all_fragments.games

import androidx.lifecycle.ViewModel
import com.android.multistream.di.main_activity.ViewModelKey
import com.android.multistream.di.main_activity.main_fragments.view_all_fragments.ViewAllFragmentScope
import com.android.multistream.ui.main_activity.fragments.view_all_fragments.mixer.top_games_view_all.MixerTopGamesViewAllViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class MixerTopGamesViewAllViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(MixerTopGamesViewAllViewModel::class)
    @ViewAllFragmentScope
    abstract fun getMixerTopGamesViewAll(viewModel: MixerTopGamesViewAllViewModel) : ViewModel
}