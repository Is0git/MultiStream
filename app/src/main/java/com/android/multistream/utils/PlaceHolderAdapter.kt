package com.android.multistream.utils

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.children
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.android.multistream.ui.main.fragments.browse_fragment.ItemHoverViewHolder
import com.example.multistreamaterialplaceholdercard.PlaceHolderMaterialCardView
import com.example.multistreamaterialplaceholdercard.listeners.PlaceHolderViewListener

class PlaceHolderAdapter<T, K : ViewDataBinding>(
    private val itemLayoutId: Int,
    var onShowAnimation: Boolean = false,
    var cancelAnimator: (K, T) -> Unit
) :
    RecyclerView.Adapter<PlaceHolderAdapter.MyViewHolder<K>>() {

    var data: List<T>? = null
        set(value) {
            field = value
            notifyDataSetChanged()
        }
    var onClickListener: PlaceHolderViewListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder<K> {
        val binding = DataBindingUtil.inflate<K>(
            LayoutInflater.from(parent.context),
            itemLayoutId,
            parent,
            false
        )
        return MyViewHolder(binding, onShowAnimation).also { it.placeHolderListener = this.onClickListener }
    }

    fun setOnItemClickListener(onClick: (position: Int, itemView: View) -> Unit) {
        onClickListener = object : PlaceHolderViewListener {
            override fun onClick(position: Int, itemView: View) {
                onClick(position, itemView)
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

            (holder.dataBinding.root as ViewGroup).children.forEach {
                it.visibility = View.VISIBLE
            }
        }
    }

    class MyViewHolder<K : ViewDataBinding>(val dataBinding: K, onShowPressAnimation: Boolean) :
        ItemHoverViewHolder<K>(dataBinding, onShowPress = onShowPressAnimation) {

        var placeHolderListener: PlaceHolderViewListener? =  null

        override fun backgroundAnimation() {
        }

        override fun navigate(binding: K) {
            placeHolderListener?.onClick(adapterPosition, itemView)
        }
    }
}