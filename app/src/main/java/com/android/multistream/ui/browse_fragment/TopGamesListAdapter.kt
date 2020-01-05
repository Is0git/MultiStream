package com.android.multistream.ui.browse_fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.android.multistream.database.entities.TopGames
import com.android.multistream.databinding.BrowseFragmentBinding
import com.android.multistream.databinding.TopGamesListBinding
import com.android.multistream.di.MainActivity.browse_fragment.BrowseFragmentScope
import javax.inject.Inject

@BrowseFragmentScope
class TopGamesListAdapter @Inject constructor() : PagedListAdapter<TopGames, TopGamesListAdapter.MyViewHolder>(callback) {
    class MyViewHolder(val binding: TopGamesListBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = TopGamesListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
      holder.binding.textName.text = getItem(position)?.name
    }
}

val callback = object : DiffUtil.ItemCallback<TopGames>() {
    override fun areItemsTheSame(oldItem: TopGames, newItem: TopGames): Boolean = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: TopGames, newItem: TopGames): Boolean = oldItem == newItem

}