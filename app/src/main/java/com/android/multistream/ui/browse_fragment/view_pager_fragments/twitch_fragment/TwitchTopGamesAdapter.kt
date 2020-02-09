package com.android.multistream.ui.browse_fragment.view_pager_fragments.twitch_fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.multistream.R
import com.android.multistream.anim.list_item_hower_anim.ItemHowerViewHolder
import com.android.multistream.databinding.SingleTopGamesListBinding
import com.android.multistream.di.MainActivity.browse_fragment.view_pager_fragments.twitch_fragment.TwitchFragmentGamesScope
import com.android.multistream.network.twitch.models.v5.TopItem
import com.android.multistream.ui.MainActivity
import com.android.multistream.ui.browse_fragment.BrowseFragment
import com.android.multistream.utils.TWITCH
import com.android.multistream.utils.data_binding.ImageLoader
import javax.inject.Inject

@TwitchFragmentGamesScope
class TwitchTopGamesAdapter @Inject constructor() :
    RecyclerView.Adapter<TwitchTopGamesAdapter.MyViewHolder>() {
    lateinit var clickListener: OnGameCategoryListener
    var spanCount = 0
    var list: List<TopItem>? = null
        set(value) {
            val begin = if (field == null) 0 else field?.count()!! - 1
            field = value
            notifyItemRangeChanged(begin, value?.size!! - 1)
        }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val binding =
            SingleTopGamesListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                .also { it.onGameCategoryListener = this.clickListener }
        return MyViewHolder(binding, spanCount)
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
        ItemHowerViewHolder<SingleTopGamesListBinding>(listBinding, spanCount) {

        override fun navigate(binding: SingleTopGamesListBinding) {
            binding.onGameCategoryListener?.onGameClick(0, 0, null, "sds", "sdsd", "29595")
        }

        override fun backgroundAnimation() {
         val bgView =   ( (binding.root.context as MainActivity).supportFragmentManager.findFragmentById(R.id.main_fragment_container)?.childFragmentManager?.fragments?.get(0)
                    as BrowseFragment).binding.root

            ImageLoader.getImageDrawableFromUrl(bgView.context, binding.backgroundUrl, bgView)
        }
    }


}
