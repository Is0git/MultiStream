package com.android.multistream.ui.browse_fragment.view_pager_fragments.mixer_fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.multistream.databinding.MixerTopGamesListBinding
import com.android.multistream.di.MainActivity.browse_fragment.view_pager_fragments.mixer_fragment.MixerFragmentGamesScope
import com.android.multistream.network.mixer.models.top_games.MixerTopGames
import javax.inject.Inject

@MixerFragmentGamesScope
class MixerTopGamesListAdapter @Inject constructor() : RecyclerView.Adapter<MixerTopGamesListAdapter.MyViewHolder>() {

    var list: List<MixerTopGames>? = null
    set(value) {
        field = value
        notifyDataSetChanged()
    }

    class MyViewHolder(val binding: MixerTopGamesListBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
       val binding = MixerTopGamesListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int = list?.count() ?: 0

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
      holder.binding.mixerTopGames = list?.get(0)
    }
}