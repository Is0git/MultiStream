package com.android.multistream.ui.main.fragments.profile_fragment.twitch_profile

import androidx.navigation.fragment.navArgs
import com.android.multistream.R
import com.android.multistream.ui.main.fragments.profile_fragment.ProfileFragment
import com.android.multistream.ui.main.fragments.profile_fragment.twitch_profile.past_recordings.TwitchPastStreamsFragment
import com.android.multistream.ui.main.fragments.profile_fragment.twitch_profile.past_videos.TwitchClipsFragment
import com.android.multistream.utils.data_binding.ImageLoader
import kotlinx.android.synthetic.main.profile_content.view.*

class TwitchProfileFragment : ProfileFragment() {

    private val navArgs: TwitchProfileFragmentArgs by navArgs()

    override fun addViewPagerFragments() {
        createProfileFragment(getString(R.string.past_streams), TwitchPastStreamsFragment::class.java, channelId)
        createProfileFragment(getString(R.string.video_clips), TwitchClipsFragment::class.java, channelId)
    }

    override fun resolveArgs() {
            navArgs.channel?.also {
                binding.profileLayout.apply {
                    streamerUserName.text = it.display_name
                    viewersCount.text = it.views.toString()
                    followersCount.text = it.followers.toString()
                    ImageLoader.loadImageWithProgressBar(banner_image, banner_progress_bar, it.profile_banner)
                    ImageLoader.loadImage(circleImageView, it.logo)
                }
            }
    }

    override fun getChannelIdFromArgs(): String? {
        return  navArgs.channel?._id.toString()
    }

}