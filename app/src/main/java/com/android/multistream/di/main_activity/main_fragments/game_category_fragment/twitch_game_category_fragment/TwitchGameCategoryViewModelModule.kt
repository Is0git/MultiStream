package com.android.multistream.di.main_activity.main_fragments.game_category_fragment.twitch_game_category_fragment

import androidx.lifecycle.ViewModel
import com.android.multistream.di.main_activity.ViewModelKey
import com.android.multistream.ui.main_activity.fragments.game_category_fragment.twitch_game_category.TwitchGameCategoryViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class TwitchGameCategoryViewModelModule {
    @Binds
    @IntoMap
    @TwitchGameCategoryScope
    @ViewModelKey(TwitchGameCategoryViewModel::class)
    abstract fun getTwitchGameCategoryViewModel(viewModel: TwitchGameCategoryViewModel) : ViewModel
}