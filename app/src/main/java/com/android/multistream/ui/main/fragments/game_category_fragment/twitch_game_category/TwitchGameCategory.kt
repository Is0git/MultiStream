package com.android.multistream.ui.main.fragments.game_category_fragment.twitch_game_category

import android.view.View
import androidx.lifecycle.observe
import com.android.multistream.R
import com.android.multistream.databinding.ListItemTwoExtendedBinding
import com.android.multistream.network.twitch.models.new_twitch_api.channels.DataItem
import com.android.multistream.network.twitch.models.new_twitch_api.channels.GameChannels
import com.android.multistream.ui.main.fragments.game_category_fragment.GameCategoryFragment
import com.android.multistream.utils.PlaceHolderAdapter
import com.android.multistream.utils.data_binding.ImageLoader
import com.example.pagination.PageLoader

class TwitchGameCategory : GameCategoryFragment<DataItem, TwitchGameCategoryViewModel>(TwitchGameCategoryViewModel::class.java) {
    override fun getPlaceHolderAdapter(): PlaceHolderAdapter<DataItem, ListItemTwoExtendedBinding> {
      return PlaceHolderAdapter(R.layout.list_item_two_extended, false) { k, t ->
          k.apply {
              streamTitle.text = t.title
              ImageLoader.loadImageTwitch(streamImage, t.thumbnail_url)
              ImageLoader.loadImage(streamerBanner, t.user?.logo)
              streamerName.text = t.user_name
              viewersCount.text = t.viewer_count.toString()
          }
      }
    }

    override fun onCardClick(position: Int, view: View) {

    }

    override fun observe() {
        viewModel.pageLoader.dataLiveData.observe(viewLifecycleOwner) {
            adapter.data = it
        }
    }

    override fun getPageLoader(): PageLoader<*> {
        return viewModel.pageLoader
    }
}