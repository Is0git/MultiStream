package com.android.multistream.utils

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.android.multistream.anim.anim_decorations.alphaAnimate
import com.android.multistream.anim.list_item_hower_anim.ItemHowerViewHolder
import com.android.multistream.databinding.ChannelsViewPagerItemBinding

class PlaceHolderAdapter<T, K : ViewDataBinding>(val itemLayoutId: Int, var onShowAnimation: Boolean = false, var cancelAnimator: (K) -> Unit) :
    RecyclerView.Adapter<PlaceHolderAdapter.MyViewHolder<K>>() {

    var data: MutableList<T>? = null
    set(value) {
        field = value
        notifyDataSetChanged()

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder<K> {
      val binding = DataBindingUtil.inflate<K>(LayoutInflater.from(parent.context), itemLayoutId, parent, false)
        return  MyViewHolder(binding, onShowAnimation)
    }

    override fun getItemCount(): Int = data?.count() ?: 3

    override fun onBindViewHolder(holder: MyViewHolder<K>, position: Int) {
        if (data != null) {
            cancelAnimator(holder.binding)
        }
    }


    class MyViewHolder<K : ViewDataBinding>(val dataBinding: K, onShowPressAnimation: Boolean) :
        ItemHowerViewHolder<K>(dataBinding, onShowPress = onShowPressAnimation) {
        override fun backgroundAnimation() {

        }

        override fun navigate(binding: K) {

        }

    }

}