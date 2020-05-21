package com.android.multistream.ui.main.fragments.profile_fragment.twitch_profile.past_recordings

import android.view.View
import android.widget.AdapterView
import android.widget.TextView
import com.android.multistream.network.twitch.models.new_twitch_api.videos.DataItem
import com.android.multistream.ui.main.fragments.profile_fragment.RecordingFragment
import com.android.multistream.utils.NumbersConverter
import com.android.multistream.utils.data_binding.ImageLoader
import com.example.pagination.PageLoader
import kotlinx.android.synthetic.main.past_videos_pickers_layout.view.*

class TwitchPastStreamsFragment :
    RecordingFragment<TwitchPastStreamsViewModel, DataItem>(TwitchPastStreamsViewModel::class.java) {

    override fun getCategoriesMap(): Map<String, String>? {
        return viewModel.repo.categoriesMap
    }

    override fun getSortMap(): Map<String, String>? {
        return viewModel.repo.sortMap
    }

    override fun getPeriodMap(): Map<String, String> {
        return viewModel.repo.periodMap
    }

    override fun getPageLoader(): PageLoader<DataItem> {
        return viewModel.pageLoader
    }

    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        viewModel.repo.apply {
            binding.pickerLayout.also {
                when (parent?.adapter) {
                    it.category_drop_down.adapter -> type = resolvePickedItem(0, view)
                    it.sort_drop_down.adapter -> sort = resolvePickedItem(1, view)
                    it.period_drop_down.adapter -> period = resolvePickedItem(2, view)
                }
            }
        }
        viewModel.repo.after = null
        super.onItemClick(parent, view, position, id)
    }

    private fun resolvePickedItem(position: Int, view: View?): String? {
        return pickersList[position][(view as TextView).text.toString()]
    }

    override fun onBind(): (PastStreamAdapter.VideosViewHolder, Int, DataItem) -> Unit {
        return { holder, p, item ->
            holder.binding.apply {
                ImageLoader.loadImageTwitch(streamThumbNail, removeWrongUrl(item.thumbnail_url))
                title.text = item.title
                timeText.text = item.duration
                dateText.text = item.created_at
                viewersCount.text = NumbersConverter.getK(item.view_count, requireContext())
            }
        }
    }

    override fun setId(id: String?) {
        viewModel.repo.userId = id
    }
}

fun removeWrongUrl(url: String?): String? {
    return url?.filterNot { it == '%' }
}