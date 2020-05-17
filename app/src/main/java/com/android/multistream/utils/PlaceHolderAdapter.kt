package com.android.multistream.utils

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.children
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.android.multistream.anim.list_item_hower_anim.ItemHoverViewHolder
import com.android.multistream.ui.widgets.place_holder_material_card.PlaceHolderMaterialCardView
import com.android.multistream.ui.widgets.place_holder_material_card.listeners.PlaceHolderViewListener

class PlaceHolderAdapter<T, K : ViewDataBinding>(
    private val itemLayoutId: Int,
    var onShowAnimation: Boolean = false,
    var cancelAnimator: (K, T) -> Unit
) :
    RecyclerView.Adapter<PlaceHolderAdapter.MyViewHolder<K>>() {

    var data: MutableList<T>? = null
        set(value) {
            field = value
            notifyDataSetChanged()
        }
    var onClickListener: PlaceHolderViewListener<T>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder<K> {
        val binding = DataBindingUtil.inflate<K>(
            LayoutInflater.from(parent.context),
            itemLayoutId,
            parent,
            false
        )
        return MyViewHolder(binding, onShowAnimation)
    }

    fun setOnItemClickListener(onClick: (T) -> Unit) {
        onClickListener = object : PlaceHolderViewListener<T> {
            override fun onClick(item: T) {
                onClick(item)
            }
        }
    }

    override fun getItemCount(): Int = data?.count() ?: 3

    override fun onBindViewHolder(holder: MyViewHolder<K>, position: Int) {
        if (data != null) {
            (holder.dataBinding.root as PlaceHolderMaterialCardView).apply {
                cancelAnimation()
                removeView(placeHolderView)
            }
            val item = data?.get(position)!!

            cancelAnimator(holder.binding, item)

            holder.itemView.setOnClickListener { onClickListener?.onClick(item) }

            (holder.dataBinding.root as ViewGroup).children.forEach {
                it.visibility = View.VISIBLE
            }
        }
    }

    class MyViewHolder<K : ViewDataBinding>(val dataBinding: K, onShowPressAnimation: Boolean) :
        ItemHoverViewHolder<K>(dataBinding, onShowPress = onShowPressAnimation) {

        override fun backgroundAnimation() {
        }

        override fun navigate(binding: K) {
        }
    }
}