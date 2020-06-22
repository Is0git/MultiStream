package com.iso.multistream.ui.main_activity.fragments.profile_fragment.mixer_profile

import android.view.View
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import com.iso.multistream.R
import com.iso.multistream.ui.main_activity.fragments.profile_fragment.ProfileFragment
import com.iso.multistream.ui.main_activity.fragments.profile_fragment.mixer_profile.vods.MixerVodsFragment
import com.iso.multistream.utils.data_binding.ImageLoader
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

    override fun arePermissionGranted(): Boolean {
        return false
    }

    override fun onWatchButtonClick() {
       Snackbar.make(binding.profileLayout, "mixer player is disabled", Snackbar.LENGTH_SHORT).show()
    }
}