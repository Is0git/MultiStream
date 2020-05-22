package com.android.multistream.ui.main.fragments.view_all_fragments

import android.graphics.drawable.AnimatedVectorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.android.multistream.R
import com.multistream.multistreamsearchview.search_view.OnItemClickListener

class ViewAllListAdapter<T>(
    var type: Int
) : RecyclerView.Adapter<ViewAllListAdapter.SearchViewHolder>() {
    companion object {
        const val GAMES = 0
        const val CHANNELS = 1
        const val STREAMS = 2
    }

    var onBind: ((SearchViewHolder, T?, Int) -> Unit)? = null
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
    var onItemClickListener: OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val viewHolder = when (viewType) {
            GAMES -> {
                val view = layoutInflater.inflate(R.layout.view_all_game_item, parent, false)
                SearchViewHolder.GamesViewHolder(view)
            }
            CHANNELS -> {
                val view = layoutInflater.inflate(R.layout.view_all_channel_item, parent, false)
                SearchViewHolder.ChannelsViewHolder(view)
            }
            else -> {
                val view = layoutInflater.inflate(R.layout.view_all_stream_item, parent, false)
                SearchViewHolder.StreamsViewHolder(view)
            }
        }
        return viewHolder.apply {
            itemView.setOnClickListener {
                onItemClickListener?.onClick(adapterPosition, it)
            }
        }
    }

    override fun getItemCount(): Int {
        return data?.count() ?: 0
    }

    override fun getItemViewType(position: Int): Int {
        return when (type) {
            GAMES -> GAMES
            STREAMS -> STREAMS
            CHANNELS -> CHANNELS
            else -> GAMES
        }
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        onBind?.invoke(holder, data?.get(position), position)
    }

    sealed class SearchViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val image: ImageView = view.findViewById(R.id.image)
        val text: TextView = view.findViewById(R.id.text)

        class ChannelsViewHolder(view: View) : SearchViewHolder(view) {
            var viewersText: TextView = view.findViewById(R.id.viewers_text)
            var followersCount: TextView = view.findViewById(R.id.followers_text)
        }
        class GamesViewHolder(view: View) : SearchViewHolder(view) {

            var viewerCount: TextView = view.findViewById(R.id.viewers_count)
            var channelsCount: TextView = view.findViewById(R.id.channels_count)
        }
        class StreamsViewHolder(view: View) : SearchViewHolder(view) {

            private var liveImage: ImageView = view.findViewById(R.id.live_image)
            var gameText: TextView = view.findViewById(R.id.game_text)
            var viewersText: TextView = view.findViewById(R.id.followers_text)
            var liveImageDrawable: AnimatedVectorDrawable? = null

            init {
                liveImageDrawable =
                    if (liveImage.drawable is AnimatedVectorDrawable) liveImage.drawable as AnimatedVectorDrawable else null
            }

            fun playLiveVectorAnimation() {
                liveImageDrawable?.start()
            }

            fun stopLiveVectorAnimation() {
                liveImageDrawable?.stop()
            }
        }
    }


    override fun onViewAttachedToWindow(holder: SearchViewHolder) {
        if (holder is SearchViewHolder.StreamsViewHolder) {
            holder.playLiveVectorAnimation()
        }
        super.onViewAttachedToWindow(holder)
    }

    override fun onViewDetachedFromWindow(holder: SearchViewHolder) {
        if (holder is SearchViewHolder.StreamsViewHolder) {
            holder.stopLiveVectorAnimation()
        }
        super.onViewDetachedFromWindow(holder)
    }

}