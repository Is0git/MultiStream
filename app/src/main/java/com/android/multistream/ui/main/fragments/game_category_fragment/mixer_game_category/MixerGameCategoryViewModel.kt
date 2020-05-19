package com.android.multistream.ui.main.fragments.game_category_fragment.mixer_game_category

import androidx.lifecycle.ViewModel
import com.android.multistream.di.main_activity.main_fragments.game_category_fragment.mixer_game_category_fragment.MixerGameCategoryScope
import javax.inject.Inject

@MixerGameCategoryScope
class MixerGameCategoryViewModel @Inject constructor(var repo: MixerGameCategoryRepository) : ViewModel() {
    var pageLoader = repo.pageLoader
}