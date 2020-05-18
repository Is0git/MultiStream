package com.android.multistream.ui.player.fragments

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.android.multistream.R
import com.android.multistream.auth.platforms.TwitchPlatform
import com.android.multistream.ui.main_activity.MainActivityViewModel
import com.android.player.chat.chat_factories.PlayerType
import com.android.player.ui.MultiStreamPlayerLayout


class PlayerFragment : Fragment() {

    private var channelName: String = "drdisrespect"
    private var title: String = "title"
    private var category: String? = "category"
    private var displayName: String? = "name"
    private var imageUrl: String? = null
    lateinit var binding: ViewDataBinding
    lateinit var mainViewModel: MainActivityViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreate(savedInstanceState)
        getArgs()
        mainViewModel = ViewModelProvider(requireActivity()).get(MainActivityViewModel::class.java)
        return if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) inflater.inflate(
            R.layout.player_layout,
            container,
            false
        ) else inflater.inflate(R.layout.player_layout_land, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initPlayer()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        (view as MultiStreamPlayerLayout).onRotation(newConfig)
    }

    private fun getArgs() {
        arguments?.apply {
            channelName = getString("channel_name", "null")
            title = getString("channel_title", "null")
            category = getString("channel_category", "null")
            displayName = getString("channel_display_name", "null")
            imageUrl = getString("channel_image", "null")
        }
    }

    private fun initPlayer() {
        (view as MultiStreamPlayerLayout).apply {
            val playerType =
                PlayerType.TwitchChatType(
                    mainViewModel.getTwitchUser()?.name,
                    mainViewModel.getToken(TwitchPlatform::class.java),
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
    }

    override fun onDestroyView() {
        (view as MultiStreamPlayerLayout).release()
        super.onDestroyView()
    }
}