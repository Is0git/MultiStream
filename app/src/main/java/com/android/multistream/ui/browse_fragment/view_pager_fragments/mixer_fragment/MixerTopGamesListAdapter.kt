package com.android.multistream.ui.browse_fragment.view_pager_fragments.mixer_fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.multistream.databinding.SingleTopGamesListBinding
import com.android.multistream.di.MainActivity.browse_fragment.view_pager_fragments.mixer_fragment.MixerFragmentGamesScope
import com.android.multistream.network.mixer.models.top_games.MixerTopGames
import com.android.multistream.ui.browse_fragment.view_pager_fragments.twitch_fragment.OnGameCategoryListener
import com.android.multistream.utils.MIXER
import javax.inject.Inject

@MixerFragmentGamesScope
class MixerTopGamesListAdapter @Inject constructor() :
    RecyclerView.Adapter<MixerTopGamesListAdapter.MyViewHolder>() {

    lateinit var onGameCategoryListener: OnGameCategoryListener
    var list: List<MixerTopGames>? = null
        set(value) {
            val begin = if (field == null) 0 else field?.count()!! - 1
            field = value
            notifyItemRangeChanged(begin, value?.size!! - 1)
        }

    class MyViewHolder(val binding: SingleTopGamesListBinding) :
        RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding =
            SingleTopGamesListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                .also { it.onGameCategoryListener = this.onGameCategoryListener }
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int = list?.count() ?: 0

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.binding.apply {
            backgroundUrl = list?.get(position)?.backgroundUrl
            id = list?.get(position)?.id.toString()
            gameName = list?.get(position)?.name
            platformType = MIXER
            viewersCurrent = list?.get(position)?.viewersCurrent
        }


    }
}