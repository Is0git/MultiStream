package com.android.multistream.ui.main_activity.fragments.browse_fragment.view_pager_fragments.mixer_fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.multistream.ui.main_activity.fragments.browse_fragment.ItemHoverViewHolder
import com.android.multistream.databinding.SingleTopGamesListBinding
import com.android.multistream.di.main_activity.main_fragments.browse_fragment.view_pager_fragments.mixer_fragment.MixerGamesBrowseFragmentScope
import com.android.multistream.network.mixer.models.top_games.MixerTopGames
import com.android.multistream.utils.NumbersConverter
import com.android.multistream.utils.data_binding.ImageLoader
import com.multistream.multistreamsearchview.search_view.OnItemClickListener
import javax.inject.Inject

@MixerGamesBrowseFragmentScope
class MixerTopGamesListAdapter @Inject constructor() :
    RecyclerView.Adapter<MixerTopGamesListAdapter.MyViewHolder>() {
    var spanCount = 0
    var itemClickListener: OnItemClickListener? = null
    var list: List<MixerTopGames>? = null
        set(value) {
            val begin = if (field == null) 0 else 0.coerceAtLeast(field?.count()!! - 1)
            field = value
            if (!value.isNullOrEmpty()) notifyItemRangeChanged(begin, 0.coerceAtLeast(value.size))
        }

    class MyViewHolder(val listBinding: SingleTopGamesListBinding, spanCount: Int = 0) :
        ItemHoverViewHolder<SingleTopGamesListBinding>(listBinding, spanCount, true) {

        var itemClickListener: OnItemClickListener? = null
        override fun navigate(binding: SingleTopGamesListBinding) {
            itemClickListener?.onClick(adapterPosition, itemView)
        }

        override fun backgroundAnimation() {

        }

    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        this.spanCount = (recyclerView.layoutManager as GridLayoutManager).spanCount
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding =
            SingleTopGamesListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding, spanCount).also {
            it.itemClickListener = this.itemClickListener
        }
    }

    override fun getItemCount(): Int = list?.count() ?: 0

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = list?.get(position)
        holder.binding.apply {
            ImageLoader.loadImage(gameImage, item?.backgroundUrl)
            streamTitle.text = item?.name
            viewersCount.text = NumbersConverter.getK(item?.viewersCurrent, gameImage.context)
        }
    }
}