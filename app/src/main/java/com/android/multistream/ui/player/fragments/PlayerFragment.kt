package com.android.multistream.ui.player.fragments

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.NestedScrollingParent2
import androidx.databinding.ViewDataBinding
import androidx.dynamicanimation.animation.DynamicAnimation
import androidx.dynamicanimation.animation.FlingAnimation
import androidx.fragment.app.Fragment
import com.android.multistream.R
import com.android.player.ui.MultiStreamPlayerLayout
import com.android.player.chat.chat_factories.PlayerType


class PlayerFragment : Fragment() {


    private var channelName = "drdisrespect"
    private var title = "title"
    private var category = "category"
    private var displayName = "name"
    private var imageUrl: String? = null
    lateinit var binding: ViewDataBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreate(savedInstanceState)
        channelName = arguments?.getString("channel_name", "drdisrespect") ?: "sds"
        title = arguments?.getString("channel_title", "drdisrespect") ?: "sds"
        category = arguments?.getString("channel_category", "drdisrespect") ?: "sds"
        displayName = arguments?.getString("channel_display_name", "null") ?: "sds"
        imageUrl = arguments?.getString("channel_image", "drdisrespect") ?: "sds"
        val view =
            if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) inflater.inflate(
                R.layout.player_layout,
                container,
                false
            ) else inflater.inflate(R.layout.player_layout_land, container, false)
        (view as MultiStreamPlayerLayout).apply {
            val playerType =
                PlayerType.TwitchChatType(
                    "is0xxx",
                    "7uyg0kooxcagt096sig5f2i023mrdk",
                    channelName,
                    title,
                    displayName,
                    category,
                    imageUrl,
                    null,
                    null
                )
            initializePlayer(playerType)
        }

        return view
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        (view as MultiStreamPlayerLayout).onRotation(newConfig)
    }

    override fun onDestroyView() {
        (view as MultiStreamPlayerLayout).release()
        super.onDestroyView()
    }
}