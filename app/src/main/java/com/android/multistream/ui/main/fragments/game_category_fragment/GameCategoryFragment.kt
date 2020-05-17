package com.android.multistream.ui.main.fragments.game_category_fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import com.android.multistream.R
import com.android.multistream.databinding.GameCategoryLayoutBinding
import com.android.multistream.databinding.ListItemTwoExtendedBinding
import com.android.multistream.network.twitch.models.v5.followed_streams.StreamsItem
import com.android.multistream.utils.PlaceHolderAdapter
import com.android.multistream.utils.data_binding.ImageLoader

class GameCategoryFragment : Fragment() {

    lateinit var binding: GameCategoryLayoutBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = GameCategoryLayoutBinding.inflate(inflater, container, false)
        val items = mutableListOf(
            StreamsItem(null, null, null, null, null, null),
            StreamsItem(null, null, null, null, null, null),
            StreamsItem(null, null, null, null, null, null),
            StreamsItem(null, null, null, null, null, null)
        )
        val adapter =
            PlaceHolderAdapter<StreamsItem, ListItemTwoExtendedBinding>(
                R.layout.list_item_two_extended,
                false
            ) { k, t ->
                k.apply {
                    viewersCount.text = "2121"
                    ImageLoader.loadImageTwitch(
                        streamImage,
                        "https://static-cdn.jtvnw.net/previews-ttv/live_user_fextralife-440x248.jpg"
                    )
                    t.channel.also { channel ->
                        streamTitle.text = "DROPS ALL DAY KAPPA"
                        ImageLoader.loadImageTwitch(
                            streamerBanner,
                            "https://static-cdn.jtvnw.net/jtv_user_pictures/anomaly-profile_image-0be1a6abbc7a9f45-50x50.png"
                        )
                        streamGame.text = "VALORANT"
                        streamerName.text = "EXODIA"
                    }
                }
            }
        binding.filledExposedDropdown.setAdapter(
            ArrayAdapter<String>(
                requireContext(),
                R.layout.drop_down_menu_item,
                Array<String>(2) { "Hi" })
        )
        adapter.data = items
        binding.channelsRecyclerview.adapter = adapter
        return binding.root
    }
}