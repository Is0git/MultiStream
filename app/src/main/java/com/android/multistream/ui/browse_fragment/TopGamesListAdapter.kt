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

const val TOP = 0
const val TWITCH = 1
const val MIXER = 2

@BrowseFragmentScope
class TopGamesListAdapter @Inject constructor() : RecyclerView.Adapter<TopGamesListAdapter.MyViewHolder>() {

    var type = 0
    set(value) {
        field = value
        sortList(value)
    }
    var list: List<Data>? = null
    set(value) {
        field = value
        sortList(type)
        notifyDataSetChanged()
    }
    var filteredList: List<Data>? = null

    set(value) {
        val res = if (field != null && field?.isNotEmpty()!!) field?.size!! - 1 else 0
        field = value
        notifyDataSetChanged()
    }
    class MyViewHolder(val binding: TopGamesListBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = TopGamesListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
      holder.binding.gamesData = filteredList?.get(position)
    }

    fun sortList(type: Int) {
        val result = when(type) {
            TOP -> list
            TWITCH -> list?.filter {it.platformType == "twitch"}
            MIXER -> list?.filter {it.platformType == "mixer"}
            else -> return
        }
        filteredList = result
    }

    override fun getItemCount(): Int = filteredList?.count() ?: 0
}

val callback = object : DiffUtil.ItemCallback<Data>() {
    override fun areItemsTheSame(oldItem: Data, newItem: Data): Boolean = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Data, newItem: Data): Boolean = oldItem == newItem

}