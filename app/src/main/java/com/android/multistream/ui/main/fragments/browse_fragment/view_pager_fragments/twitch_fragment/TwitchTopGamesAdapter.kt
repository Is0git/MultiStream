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
import com.android.multistream.utils.NumbersConverter
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


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding =
            SingleTopGamesListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding, spanCount).also { it.itemClickListener = this.clickListener }
    }

    override fun getItemCount(): Int = list?.count() ?: 0

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = list?.get(position)
        holder.listBinding.apply {
            ImageLoader.loadImageTwitchWithParams(gameImage, item?.game?.box?.template, 200, 125)
            streamTitle.text = item?.game?.name
            viewersCount.text = NumbersConverter.getK(item?.viewers, gameImage.context)
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

        }
    }


}
