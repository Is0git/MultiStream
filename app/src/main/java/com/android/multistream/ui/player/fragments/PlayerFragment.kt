package com.android.multistream.ui.player.fragments

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import com.android.multistream.R
import com.android.multistream.auth.platforms.TwitchPlatform
import com.android.multistream.ui.main_activity.MainActivity
import com.android.multistream.ui.main_activity.MainActivityViewModel
import com.android.multistream.utils.NumbersConverter
import com.android.multistream.utils.data_binding.ImageLoader
import com.android.player.ui.MultiStreamPlayerLayout
import com.android.player.ui.OnFollowButtonListener
import com.example.daggerviewmodelfragment.DaggerViewModelFragment


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
                followButton.visibility = View.GONE
                chatInputGroup.visibility = View.GONE
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
                channelNameView.text = it?.stream?.channel?.displayName
                categoryView.text = it?.stream?.game
                titleTextView.text = it?.stream?.channel?.status
                ImageLoader.loadImage(profileImageView, it?.stream?.channel?.logo)
                viewersCount.text = NumbersConverter.getK(it?.stream?.viewers, requireContext())
            }
//            viewModel.getStream(channelId)
        }
//        (requireActivity() as MainActivity).binding.motionLayout.playerViewState =
//            mainViewModel.playerState
        initPlayer()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        val fragmentTransitionState = savedInstanceState?.getInt("fragment_transition_state", R.id.start)
        if (fragmentTransitionState != null) (view as MultiStreamPlayerLayout).transitionToState(fragmentTransitionState)

        super.onActivityCreated(savedInstanceState)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putInt("fragment_transition_state", (view as MultiStreamPlayerLayout).currentConstraint)
        super.onSaveInstanceState(outState)
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

    private fun controllerMinimizeAnimation(progress: Float) {
        val alpha = 1 - progress
        (view as MultiStreamPlayerLayout).apply {
            this.settingsIconView.alpha = alpha
            this.alarmImageButton.alpha = alpha
            this.settingsIconView.alpha = alpha
            this.fullscreenButton.alpha = alpha
            this.minimizeButton.scaleY = 1 - (0.3f * progress)
            this.minimizeButton.scaleX = 1 - (0.3f * progress)
            this.followButton.alpha = alpha
            this.minimizeButton.rotation = 180 * progress
        }
    }

    private fun controllerSlideAnimation(progress: Float) {
        val alpha = 1 - progress
        (view as MultiStreamPlayerLayout).apply {
            this.liveIcon.alpha = alpha
            this.viewersCount.alpha = alpha
            if (progress < 0.5f) this.minimizeButton?.rotation = 180 + (180 * progress)
            this.minimizeButton.alpha =
                if (progress > 0.50) 1 - ((progress - 0.50f) / 0.5f) else 1f

        }
    }


    override fun onTransitionTrigger(p0: MotionLayout?, p1: Int, p2: Boolean, p3: Float) {

    }

    override fun onTransitionStarted(p0: MotionLayout?, p1: Int, p2: Int) {
        (view as MultiStreamPlayerLayout).apply {
            startConstraint = p1
            endConstraint = p2
        }
        if (p1 == R.id.start && p2 == R.id.end) setStartTransition()
    }

    override fun onTransitionChange(p0: MotionLayout?, p1: Int, p2: Int, p3: Float) {
        Log.d("PROGRESS", "$p3")
        (view as MultiStreamPlayerLayout).apply {
            if (currentTransition == com.android.player.R.id.fragment_minimize_transition) {
                controllerMinimizeAnimation(p3)
                (requireActivity() as MainActivity).binding.motionLayout.progress = 1 - p3
            } else {
                controllerSlideAnimation(p3)
            }
        }
    }

    override fun onTransitionCompleted(p0: MotionLayout?, p1: Int) {
        (view as MultiStreamPlayerLayout).apply {
            currentConstraint = p1
            isStateUndefined = p1 == com.android.player.R.id.end
        }
        if (p1 == R.id.slide) fragmentManager?.beginTransaction()?.remove(this)?.commit()
    }

    private fun MultiStreamPlayerLayout.isEndConstraint(constraintId: Int): Boolean {
        return endConstraint == constraintId
    }

    private fun setStartTransition() {
        (requireActivity() as MainActivity).binding.motionLayout.setTransition(R.id.hide_bars_transition)
    }

    abstract fun getPlayerLayout(): Int
    abstract fun getPlayerLayoutLand(): Int
    abstract fun initPlayer()
}