package com.android.multistream.ui.browse_fragment.view_pager_fragments.twitch_fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.multistream.databinding.MixerTopGamesListBinding
import com.android.multistream.databinding.TwitchTopGamesListBinding
import com.android.multistream.di.MainActivity.browse_fragment.view_pager_fragments.twitch_fragment.TwitchFragmentGamesScope
import com.android.multistream.network.mixer.models.top_games.MixerTopGames
import com.android.multistream.network.twitch.models.TopGames
import com.android.multistream.network.twitch.models.v5.TopItem
import com.android.multistream.ui.browse_fragment.view_pager_fragments.mixer_fragment.MixerTopGamesListAdapter
import javax.inject.Inject

@TwitchFragmentGamesScope
class TwitchTopGamesAdapter @Inject constructor() : RecyclerView.Adapter<TwitchTopGamesAdapter.MyViewHolder>() {

    var list: List<TopItem>? = null
        set(value) {
            val begin = if (field == null) 0 else field?.count()!! - 1
            field = value
            notifyItemRangeChanged(begin, value?.size!! - 1)
        }

    class MyViewHolder(val binding: TwitchTopGamesListBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = TwitchTopGamesListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int = list?.count() ?: 0

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.binding.topItem = list?.get(position)
    }
}
