package com.android.multistream.ui.main.fragments.game_channels_fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.multistream.databinding.ChannelsListBinding
import com.android.multistream.di.MainActivity.main_fragments.game_channels_fragment.GameChannelsFragmentScope
import com.android.multistream.network.twitch.models.channels.DataItem
import javax.inject.Inject

@GameChannelsFragmentScope
class TwitchChannelsList @Inject constructor(): RecyclerView.Adapter<TwitchChannelsList.MyViewHolder>() {

    var list: List<DataItem>? = null
        set(value) {
            val begin = if (field == null) 0 else field?.count()!! - 1
            field = value
            notifyItemRangeChanged(begin, value?.size!! - 1)
        }

    class MyViewHolder(val binding: ChannelsListBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ChannelsListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int = list?.count() ?: 0

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.binding.dataItem = list?.get(position)
    }
}