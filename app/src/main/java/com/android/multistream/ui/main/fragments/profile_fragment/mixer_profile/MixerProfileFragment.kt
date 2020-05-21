package com.android.multistream.ui.main.fragments.profile_fragment.mixer_profile

import androidx.navigation.fragment.navArgs
import com.android.multistream.R
import com.android.multistream.ui.main.fragments.profile_fragment.ProfileFragment
import com.android.multistream.ui.main.fragments.profile_fragment.mixer_profile.vods.MixerVodsFragment
import com.android.multistream.utils.data_binding.ImageLoader
import kotlinx.android.synthetic.main.profile_content.view.*

class MixerProfileFragment : ProfileFragment() {

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
                ImageLoader.loadImage(banner_image, it.bannerUrl)
                ImageLoader.loadImage(circleImageView, it.thumbnail?.url)
            }
        }
    }

    override fun getChannelIdFromArgs(): String? {
        return navArgs.mixerChannel?.id.toString()
    }
}