package com.android.multistream.ui.main.fragments.profile_fragment.twitch_profile.past_videos

import com.android.multistream.network.twitch.models.new_twitch_api.clips.DataItem
import com.android.multistream.ui.main.fragments.profile_fragment.RecordingFragment
import com.android.multistream.ui.main.fragments.profile_fragment.twitch_profile.past_recordings.PastStreamAdapter
import com.android.multistream.ui.main.fragments.profile_fragment.twitch_profile.past_recordings.removeWrongUrl
import com.android.multistream.utils.NumbersConverter
import com.android.multistream.utils.data_binding.ImageLoader
import com.example.pagination.PageLoader

class TwitchClipsFragment :
    RecordingFragment<TwitchPastVideosViewModel, DataItem>(TwitchPastVideosViewModel::class.java) {

    override fun onBind(): (holder: PastStreamAdapter.VideosViewHolder, position: Int, item: DataItem) -> Unit {
        return { holder, p, item ->
            holder.binding.apply {
                ImageLoader.loadImageTwitch(streamThumbNail, removeWrongUrl(item.thumbnail_url))
                title.text = item.title
                timeText.text = "0:30"
                dateText.text = item.created_at
                viewersCount.text = NumbersConverter.getK(item.view_count, requireContext())
            }
        }
    }

    override fun getCategoriesMap(): Map<String, String>? {
        return null
    }

    override fun getSortMap(): Map<String, String>? {
        return null
    }

    override fun getPeriodMap(): Map<String, String>? {
        return null
    }

    override fun getPageLoader(): PageLoader<DataItem> {
       return viewModel.pageLoader
    }

    override fun setId(id: String?) {
       viewModel.repo.userId = id
    }
}