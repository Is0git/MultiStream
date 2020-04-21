package com.android.multistream.ui.main.fragments.browse_fragment.view_pager_fragments.top_fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.multistream.anim.list_item_hower_anim.ItemHoverViewHolder
import com.android.multistream.databinding.SingleTopGamesListBinding
import com.android.multistream.di.main_activity.main_fragments.browse_fragment.view_pager_fragments.top_fragment.TopFragmentGamesScope
import com.android.multistream.network.twitch.models.new_twitch_api.top_games.Data
import com.android.multistream.utils.UNKNOWN
import javax.inject.Inject

@TopFragmentGamesScope
class TopGamesListAdapter @Inject constructor() :
    RecyclerView.Adapter<TopGamesListAdapter.MyViewHolder>() {
    lateinit var listener: CategoryNavigationListener<Data>
    var spanCount = 0
    var list: List<Data>? = null
        set(value) {
            val begin = if (field == null) 0 else field?.count()!! - 1
            field = value
            notifyItemRangeChanged(begin, value?.size!! - 1)
        }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        this.spanCount = (recyclerView.layoutManager as GridLayoutManager).spanCount
    }


    class MyViewHolder(val listBinding: SingleTopGamesListBinding, spanCount: Int = 0) : ItemHoverViewHolder<SingleTopGamesListBinding>(listBinding, spanCount ) {
        override fun navigate(binding: SingleTopGamesListBinding) {
            listBinding.root.callOnClick()
        }

        override fun backgroundAnimation() {

        }

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TopGamesListAdapter.MyViewHolder {
        val binding =
            SingleTopGamesListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding, spanCount)
    }

    override fun getItemCount(): Int = list?.count() ?: 0

    override fun onBindViewHolder(holder: TopGamesListAdapter.MyViewHolder, position: Int) {
        holder.binding.apply {
            backgroundUrl = list?.get(position)?.box_art_url
            id = list?.get(position)?.id.toString()
            gameName = list?.get(position)?.name
            platformType = UNKNOWN
            viewersCurrent = list?.get(position)?.viewersCount
        }
    }
}
