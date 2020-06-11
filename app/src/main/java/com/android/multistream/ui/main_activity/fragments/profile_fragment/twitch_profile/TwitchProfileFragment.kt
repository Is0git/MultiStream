package com.android.multistream.ui.main_activity.fragments.profile_fragment.twitch_profile

import android.view.View
import androidx.lifecycle.observe
import androidx.navigation.fragment.navArgs
import com.android.multistream.R
import com.android.multistream.auth.platforms.TwitchPlatform
import com.android.multistream.ui.main_activity.MainActivity
import com.android.multistream.ui.main_activity.fragments.profile_fragment.ProfileFragment
import com.android.multistream.ui.main_activity.fragments.profile_fragment.twitch_profile.past_recordings.TwitchPastStreamsFragment
import com.android.multistream.ui.main_activity.fragments.profile_fragment.twitch_profile.past_videos.TwitchClipsFragment
import com.android.multistream.utils.data_binding.ImageLoader
import kotlinx.android.synthetic.main.profile_content.view.*
import java.util.*

class TwitchProfileFragment :
    ProfileFragment<TwitchProfileViewModel>(TwitchProfileViewModel::class.java) {

    private val navArgs: TwitchProfileFragmentArgs by navArgs()

    override fun addViewPagerFragments() {
        createProfileFragment(
            getString(R.string.past_streams),
            TwitchPastStreamsFragment::class.java,
            channelId
        )
        createProfileFragment(
            getString(R.string.video_clips),
            TwitchClipsFragment::class.java,
            channelId
        )
    }

    override fun resolveArgs() {
        navArgs.streamItem?.also {
            binding.profileLayout.apply {
                streamerUserName.text = it.channel?.display_name
                viewersCount.text = it.channel?.views.toString()
                followersCount.text = it.channel?.followers.toString()
                ImageLoader.loadImageWithProgressBar(
                    banner_image,
                    banner_progress_bar,
                    it.channel?.profile_banner
                )
                ImageLoader.loadImage(circleImageView, it.channel?.logo)
            }
        }
    }

    override fun getChannelIdFromArgs(): String {
        return navArgs.streamItem?.channel?._id.toString()
    }

    override fun observeFollowing() {
        viewModel.getFollowUser(getChannelIdFromArgs())
        viewModel.followingLiveData.observe(viewLifecycleOwner) {
            binding.profileLayout.follow_button.isSelected = it != null
        }
    }

    override suspend fun onFollowClick(view: View) {
        if (view.isSelected) mainActivityViewModel.repo.followTwitchUser(channelId!!) else mainActivityViewModel.repo.unFollowTwitchUser(
            channelId!!
        )
    }

    override fun arePermissionGranted(): Boolean {
        val user = mainActivityViewModel.getTwitchUser()
        return mainActivityViewModel.isValidated(TwitchPlatform::class.java) && user != null && user.name != navArgs.streamItem?.channel?.display_name?.toLowerCase(
                Locale.getDefault())

    }

    override fun onWatchButtonClick() {
        val streamItem = navArgs.streamItem
        streamItem?.also {
            (requireActivity() as MainActivity).initLiveStreamPlayerFragment(
                        it.channel?.status, it.channel?.name, it.channel?.logo, it.game, it.channel?.display_name, getChannelIdFromArgs(), it.viewers
            )
        }

    }

}