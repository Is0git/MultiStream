package com.iso.multistream.ui.main_activity.fragments.profile_fragment.twitch_profile.past_recordings

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.iso.multistream.databinding.VideoItemLayoutBinding
import com.multistream.multistreamsearchview.search_view.OnItemClickListener
import javax.inject.Inject

class PastStreamAdapter<T> @Inject constructor() :
    RecyclerView.Adapter<PastStreamAdapter.VideosViewHolder>() {

    var onItemClickListener: OnItemClickListener? = null

    var data: List<T>? = null
        set(value) {
            when {
                value == null || value.isEmpty() -> {
                    field = value
                    notifyDataSetChanged()
                }
                else -> {
                    val begin = field?.count()
                    field = value
                    notifyItemRangeChanged(begin ?: 0, value.count())
                }
            }
        }

    var onBind: ((holder: VideosViewHolder, position: Int, item: T) -> Unit)? = null

    class VideosViewHolder(var binding: VideoItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideosViewHolder {
        val binding =
            VideoItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VideosViewHolder(binding).also { it.itemView.setOnClickListener { view -> onItemClickListener?.onClick(it.adapterPosition, view) } }
    }

    override fun onBindViewHolder(holder: VideosViewHolder, position: Int) {
        onBind?.invoke(holder, position, data?.get(position)!!)
    }

    override fun getItemCount(): Int {
        return data?.count() ?: 0
    }

}



