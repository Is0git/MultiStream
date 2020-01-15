package com.android.multistream.ui.game_channels_fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.multistream.databinding.MixerChannelsListBinding
import com.android.multistream.di.MainActivity.game_channels_fragment.GameChannelsFragmentScope
import com.android.multistream.network.mixer.models.channel.GameChannels
import com.android.multistream.network.twitch.models.channels.DataItem
import javax.inject.Inject

@GameChannelsFragmentScope
class MixerChannelsList @Inject constructor() : RecyclerView.Adapter<MixerChannelsList.MyViewHolder>() {

var list: List<GameChannels>? = null
    set(value) {
        val begin = if (field == null) 0 else field?.count()!! - 1
        field = value
        notifyItemRangeChanged(begin, value?.size!! - 1)
    }
    class MyViewHolder(val binding: MixerChannelsListBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
      val binding = MixerChannelsListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int = list?.count() ?: 0

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
      holder.binding.dataItem = list?.get(position)
    }

}