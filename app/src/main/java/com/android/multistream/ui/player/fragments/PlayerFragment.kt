package com.android.multistream.ui.player.fragments

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.work.WorkInfo
import com.android.multistream.R
import com.android.multistream.auth.platforms.TwitchPlatform
import com.android.multistream.ui.main_activity.MainActivity
import com.android.multistream.ui.main_activity.MainActivityViewModel
import com.android.multistream.utils.NumbersConverter
import com.android.multistream.utils.data_binding.ImageLoader
import com.android.player.chat.chat_factories.PlayerData
import com.android.player.ui.MultiStreamPlayerLayout
import com.android.player.ui.OnFollowButtonListener
import com.example.daggerviewmodelfragment.DaggerViewModelFragment
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout


abstract class PlayerFragment<T : PlayerFragmentViewModel<*>>(viewModelClass: Class<T>) :
    DaggerViewModelFragment<T>(viewModelClass), MotionLayout.TransitionListener {

    open lateinit var channelName: String
    open lateinit var title: String
    open lateinit var category: String
    open lateinit var displayName: String
    open var imageUrl: String? = null
    final lateinit var mainViewModel: MainActivityViewModel
    open lateinit var channelId: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreate(savedInstanceState)
        getArgs()
        mainViewModel = ViewModelProvider(requireActivity()).get(MainActivityViewModel::class.java)
        return if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) inflater.inflate(
            getPlayerLayout(),
            container,
            false
        ) else inflater.inflate(getPlayerLayoutLand(), container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setStartTransition()
        (view as MultiStreamPlayerLayout).apply {
            setTransitionListener(this@PlayerFragment)
            if (!mainViewModel.isValidated(TwitchPlatform::class.java)) {
                followButton?.visibility = View.GONE
                chatInputGroup?.visibility = View.GONE
            } else {
                viewModel.followUser.observe(viewLifecycleOwner) {
                    if (it == null) setButtonStateNotFollowing() else setButtonStateFollowing()
                }
                viewModel.getFollowUser(channelId)
            }
            onFollowButtonListener = object : OnFollowButtonListener {
                override fun follow(view: View) {
                    mainViewModel.followTwitchUser(channelId)
                }

                override fun unFollow(view: View) {
                    mainViewModel.unFollowTwitchUser(channelId)
                }

            }
            lifecycle.addObserver(viewModel)
            viewModel.userLiveData.observe(viewLifecycleOwner) {
                channelNameView?.text = it?.stream?.channel?.displayName
                categoryView?.text = it?.stream?.game
                titleTextView?.text = it?.stream?.channel?.status
                ImageLoader.loadImage(profileImageView!!, it?.stream?.channel?.logo)
                viewersCount?.text = NumbersConverter.getK(it?.stream?.viewers, requireContext())
            }
            viewModel.getStream(channelId)
        }
//        (requireActivity() as MainActivity).binding.motionLayout.playerViewState =
//            mainViewModel.playerState
        initPlayer()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
//        (requireActivity() as MainActivity).binding.motionLayout.updateLayout()
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
    }

    open fun getArgs() {
        arguments?.apply {
            channelName = getString("channel_name", "null")
            title = getString("channel_title", "null")
            category = getString("channel_category", "null")
            displayName = getString("channel_display_name", "null")
            imageUrl = getString("channel_image", "null")
            channelId = getString("channel_id", "")
        }
    }

    override fun onDestroyView() {
        (view as MultiStreamPlayerLayout).release()

        super.onDestroyView()
    }

    override fun onTransitionTrigger(p0: MotionLayout?, p1: Int, p2: Boolean, p3: Float) {

    }

    override fun onTransitionStarted(p0: MotionLayout?, p1: Int, p2: Int) {
        if (p1 == R.id.start && p2 == R.id.end) setStartTransition()
    }

    override fun onTransitionChange(p0: MotionLayout?, p1: Int, p2: Int, p3: Float) {
        Log.d("PROGRESS", "$p3")
        (requireActivity() as MainActivity).binding.motionLayout.progress = 1 - p3
    }

    override fun onTransitionCompleted(p0: MotionLayout?, p1: Int) {
            if (p1 == R.id.end) (p0 as MultiStreamPlayerLayout).playerView?.resizeMode = AspectRatioFrameLayout.RESIZE_MODE_ZOOM
    }
        fun setStartTransition() {
        (requireActivity() as MainActivity).binding.motionLayout.setTransition(R.id.hide_bars_transition)
    }

    abstract fun getPlayerLayout(): Int
    abstract fun getPlayerLayoutLand(): Int
    abstract fun initPlayer()
}