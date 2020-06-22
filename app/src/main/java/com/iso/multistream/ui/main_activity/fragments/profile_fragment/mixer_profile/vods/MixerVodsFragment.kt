package com.iso.multistream.ui.main_activity.fragments.profile_fragment.mixer_profile.vods

import android.view.View
import com.example.pagination.PageLoader
import com.iso.multistream.R
import com.iso.multistream.network.mixer.models.vods.MixerVods
import com.iso.multistream.ui.main_activity.fragments.profile_fragment.RecordingFragment
import com.iso.multistream.ui.main_activity.fragments.profile_fragment.twitch_profile.past_recordings.PastStreamAdapter
import com.iso.multistream.utils.NumbersConverter

class MixerVodsFragment : RecordingFragment<MixerVodsViewModel, MixerVods>(MixerVodsViewModel::class.java) {
    override fun onBind(): (holder: PastStreamAdapter.VideosViewHolder, position: Int, item: MixerVods) -> Unit {
        return { holder, p, item ->
            holder.binding.apply {
//                ImageLoader.loadImage(streamThumbNail, item.contentLo)
                title.text = item.name
                timeText.text = item.duration.toString()
                dateText.text = item.createdAt
                viewersCount.text = NumbersConverter.getK(item.viewsTotal, requireContext())
            }
        }
    }

    override fun getCategoriesMap(): Map<String, String>? {
       return null
    }

    override fun getSortMap(): Map<String, String>? {
        return mapOf(
            getString(R.string.default_value) to "",
            getString(R.string.views_sort) to  "viewsTotal:DESC"
        )
    }

    override fun getPeriodMap(): Map<String, String>? {
        return null
    }

    override fun getPageLoader(): PageLoader<MixerVods> {
        return viewModel.pageLoader
    }

    override fun setId(id: String?) {
        viewModel.repo.id = id
    }

    override fun onClick(position: Int, view: View) {

    }

}