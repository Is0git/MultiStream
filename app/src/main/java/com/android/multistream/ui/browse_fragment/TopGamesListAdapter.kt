package com.android.multistream.ui.browse_fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

import com.android.multistream.databinding.BrowseFragmentBinding
import com.android.multistream.databinding.TopGamesListBinding
import com.android.multistream.di.MainActivity.browse_fragment.BrowseFragmentScope
import com.android.multistream.network.twitch.models.Data
import com.android.multistream.network.twitch.models.TopGames
import javax.inject.Inject

@BrowseFragmentScope
class TopGamesListAdapter @Inject constructor() : RecyclerView.Adapter<TopGamesListAdapter.MyViewHolder>() {

    var list: List<Data>? = null

    set(value) {
        val res = if (field != null && field?.isNotEmpty()!!) field?.size!! - 1 else 0
        field = value
        notifyItemRangeChanged(res, value?.size!! - 1)
    }
    class MyViewHolder(val binding: TopGamesListBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = TopGamesListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
      holder.binding.gamesData = list?.get(position)
    }

    override fun getItemCount(): Int = list?.count() ?: 0
}

val callback = object : DiffUtil.ItemCallback<Data>() {
    override fun areItemsTheSame(oldItem: Data, newItem: Data): Boolean = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Data, newItem: Data): Boolean = oldItem == newItem

}