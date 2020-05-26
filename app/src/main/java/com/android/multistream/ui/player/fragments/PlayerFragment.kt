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
import com.android.player.chat.chat_factories.PlatformPlayerType
import com.android.player.player.MultiStreamPlayer
import com.android.player.ui.MultiStreamPlayerLayout
import javax.inject.Inject


class PlayerFragment : Fragment() {

    private var channelName: String = "drdisrespect"
    private var title: String = "title"
    private var category: String? = "category"
    private var displayName: String? = "name"
    private var imageUrl: String? = null
    lateinit var mainViewModel: MainActivityViewModel
    var multiStreamPlayer: MultiStreamPlayer? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        retainInstance = true
        mainViewModel = ViewModelProvider(requireActivity()).get(MainActivityViewModel::class.java)
        val playerType = PlatformPlayerType.TwitchChatType(
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
        multiStreamPlayer = MultiStreamPlayer.createMultiStreamPlayer(
            requireContext(),
            playerType,
            MultiStreamPlayer.LIVE_STREAM
        )
        multiStreamPlayer?.play()
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreate(savedInstanceState)
        getArgs()
        return if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) inflater.inflate(
            R.layout.player_layout,
            container,
            false
        ) else inflater.inflate(R.layout.player_layout_land, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        (view as MultiStreamPlayerLayout).multiStreamPlayer = multiStreamPlayer
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

    override fun onDestroy() {
        multiStreamPlayer?.clear()
        super.onDestroy()
    }
}