package com.android.multistream.ui.main.fragments.browse_fragment.view_pager_fragments.twitch_fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.multistream.R
import com.android.multistream.ui.main.fragments.browse_fragment.ItemHoverViewHolder
import com.android.multistream.databinding.SingleTopGamesListBinding
import com.android.multistream.di.main_activity.main_fragments.browse_fragment.view_pager_fragments.twitch_fragment.TwitchGamesBrowseFragmentScope
import com.android.multistream.network.twitch.models.v5.top_games.TopItem
import com.android.multistream.ui.main_activity.MainActivity
import com.android.multistream.ui.main.fragments.browse_fragment.BrowseFragment
import com.android.multistream.utils.TWITCH
import com.android.multistream.utils.data_binding.ImageLoader
import com.multistream.multistreamsearchview.search_view.OnItemClickListener
import javax.inject.Inject
import kotlin.math.min

@TwitchGamesBrowseFragmentScope
class TwitchTopGamesAdapter @Inject constructor() :
    RecyclerView.Adapter<TwitchTopGamesAdapter.MyViewHolder>() {
    var clickListener: OnItemClickListener? = null
    var spanCount = 0
    var list: List<TopItem>? = null
        set(value) {
            val begin = if (field == null) 0 else 0.coerceAtLeast(field?.count()!! - 1)
            field = value
            if (!value.isNullOrEmpty()) notifyItemRangeChanged(begin, 0.coerceAtLeast(value.size))
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding =
            SingleTopGamesListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding, spanCount).also { it.itemClickListener = this.clickListener }
    }

    override fun getItemCount(): Int = list?.count() ?: 0

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.listBinding.apply {
            backgroundUrl = list?.get(position)?.game?.box?.template
            id = list?.get(position)?.game?._id.toString()
            gameName = list?.get(position)?.game?.name
            platformType = TWITCH
            viewersCurrent = list?.get(position)?.viewers
        }
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        this.spanCount = (recyclerView.layoutManager as GridLayoutManager).spanCount
    }

    class MyViewHolder(val listBinding: SingleTopGamesListBinding, spanCount: Int = 0) :
        ItemHoverViewHolder<SingleTopGamesListBinding>(listBinding, spanCount, true) {

        var itemClickListener: OnItemClickListener? = null

        override fun navigate(binding: SingleTopGamesListBinding) {
            itemClickListener?.onClick(adapterPosition, itemView)
        }

        override fun backgroundAnimation() {
            val bgView =
                ((binding.root.context as MainActivity).supportFragmentManager.findFragmentById(R.id.main_fragment_container)?.childFragmentManager?.fragments?.get(0) as BrowseFragment).binding.root
            ImageLoader.getImageDrawableFromUrl(bgView.context, binding.backgroundUrl)
        }
    }


}
