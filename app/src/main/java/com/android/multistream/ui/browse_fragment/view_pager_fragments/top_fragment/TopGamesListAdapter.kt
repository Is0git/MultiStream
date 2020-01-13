package com.android.multistream.ui.browse_fragment.view_pager_fragments.top_fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.multistream.databinding.TopGamesListBinding
import com.android.multistream.di.MainActivity.browse_fragment.view_pager_fragments.top_fragment.TopFragmentGamesScope
import com.android.multistream.network.twitch.models.Data
import com.android.multistream.network.twitch.models.v5.TopItem
import javax.inject.Inject

//import android.view.LayoutInflater
//import android.view.ViewGroup
//import androidx.recyclerview.widget.DiffUtil
//import androidx.recyclerview.widget.RecyclerView
//
//import com.android.multistream.databinding.TopGamesListBinding
//import com.android.multistream.di.MainActivity.browse_fragment.BrowseFragmentScope
//import com.android.multistream.di.MainActivity.browse_fragment.view_pager_fragments.top_fragment.TopFragmentGamesScope
//import com.android.multistream.network.twitch.models.Data
//import com.android.multistream.ui.browse_fragment.CategoryNavigationListener
//import javax.inject.Inject
//
//const val TOP = 0
//const val TWITCH = 1
//const val MIXER = 2
//
//@TopFragmentGamesScope
//class TopGamesListAdapter @Inject constructor() : RecyclerView.Adapter<TopGamesListAdapter.MyViewHolder>() {
//
//    var clickListener: CategoryNavigationListener? = null
//
//    var type = 0
//    set(value) {
//        field = value
//        sortList(value)
//    }
//    var list: List<Data>? = null
//    set(value) {
//        field = value
//        sortList(type)
//        notifyDataSetChanged()
//    }
//    var filteredList: List<Data>? = null
//
//    set(value) {
//        val res = if (field != null && field?.isNotEmpty()!!) field?.size!! - 1 else 0
//        field = value
//        notifyDataSetChanged()
//    }
//    class MyViewHolder(val binding: TopGamesListBinding) : RecyclerView.ViewHolder(binding.root)
//
//
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
//        val binding = TopGamesListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
//        binding.clickListener = this.clickListener
//        return MyViewHolder(
//            binding
//        )
//    }
//
//    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
//      holder.binding.gamesData = filteredList?.get(position)
//    }
//
//    private fun sortList(type: Int) {
//        val result = when(type) {
//            TOP -> list
//            TWITCH -> list?.filter {it.platformType == "twitch"}
//            MIXER -> list?.filter {it.platformType == "mixer"}
//            else -> return
//        }
//        filteredList = result
//    }
//
////    suspend fun filterANextToB() {
////        val newList: MutableList<Data>? = null
////       for(a in 0 until list?.size!! - 20) {
////
////       }
////    }
//    override fun getItemCount(): Int = filteredList?.count() ?: 0
//}
//
//val callback = object : DiffUtil.ItemCallback<Data>() {
//    override fun areItemsTheSame(oldItem: Data, newItem: Data): Boolean = oldItem.id == newItem.id
//
//    override fun areContentsTheSame(oldItem: Data, newItem: Data): Boolean = oldItem == newItem
//
//}

@TopFragmentGamesScope
class TopGamesListAdapter @Inject constructor() :
    RecyclerView.Adapter<TopGamesListAdapter.MyViewHolder>() {

    var list: List<Data>? = null
        set(value) {
            val begin = if (field == null) 0 else field?.count()!! - 1
            field = value
            notifyItemRangeChanged(begin, value?.size!! - 1)
        }


    class MyViewHolder(val binding: TopGamesListBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TopGamesListAdapter.MyViewHolder {
        val binding =
            TopGamesListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int = list?.count() ?: 0

    override fun onBindViewHolder(holder: TopGamesListAdapter.MyViewHolder, position: Int) {
        holder.binding.data = list?.get(position)
    }
}
