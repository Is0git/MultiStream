package com.iso.multistream.di.main_activity.main_fragments.game_category_fragment.mixer_game_category_fragment

import androidx.lifecycle.ViewModel
import com.iso.multistream.di.main_activity.ViewModelKey
import com.iso.multistream.ui.main_activity.fragments.game_category_fragment.mixer_game_category.MixerGameCategoryViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class MixerGameCategoryViewModelModule {
    @Binds
    @IntoMap
    @MixerGameCategoryScope
    @ViewModelKey(MixerGameCategoryViewModel::class)
    abstract fun getMixerGameCategoryViewModel(viewModel: MixerGameCategoryViewModel) : ViewModel
}