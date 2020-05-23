package com.android.multistream.ui.main.fragments.profile_fragment.mixer_profile

import android.view.View
import androidx.navigation.fragment.navArgs
import com.android.multistream.R
import com.android.multistream.ui.main.fragments.profile_fragment.ProfileFragment
import com.android.multistream.ui.main.fragments.profile_fragment.mixer_profile.vods.MixerVodsFragment
import com.android.multistream.utils.data_binding.ImageLoader
import kotlinx.android.synthetic.main.profile_content.view.*

class MixerProfileFragment : ProfileFragment<MixerProfileViewModel>(MixerProfileViewModel::class.java) {

    private val navArgs: MixerProfileFragmentArgs by navArgs()

    override fun addViewPagerFragments() {
        createProfileFragment(getString(R.string.past_streams), MixerVodsFragment::class.java, channelId)
    }

    override fun resolveArgs() {
        navArgs.mixerChannel?.also {
            binding.profileLayout.apply {
                streamerUserName.text = it.name
                viewersCount.text = it.viewersTotal.toString()
                followersCount.text = it.numFollowers.toString()
                ImageLoader.loadImageWithProgressBar(banner_image, banner_progress_bar, it.bannerUrl)
                ImageLoader.loadImage(circleImageView, it.thumbnail?.url)
            }
        }
    }

    override fun getChannelIdFromArgs(): String {
        return navArgs.mixerChannel?.id.toString()
    }

    override fun observeFollowing() {

    }

    override suspend fun onFollowClick(view: View) {

    }
}