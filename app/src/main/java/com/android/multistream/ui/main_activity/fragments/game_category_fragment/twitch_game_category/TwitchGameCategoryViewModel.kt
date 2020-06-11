package com.android.multistream.ui.main_activity.fragments.game_category_fragment.twitch_game_category

import androidx.lifecycle.ViewModel
import com.android.multistream.di.main_activity.main_fragments.game_category_fragment.twitch_game_category_fragment.TwitchGameCategoryScope
import javax.inject.Inject

@TwitchGameCategoryScope
class TwitchGameCategoryViewModel @Inject constructor(var repository: TwitchGameCategoryRepository) : ViewModel() {

    var pageLoader = repository.pageLoader

}