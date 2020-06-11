package com.android.multistream.ui.main_activity.fragments.home_fragment.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.multistream.databinding.ChannelsViewPagerItemBinding
import com.android.multistream.di.main_activity.main_fragments.home_fragment.HomeFragmentScope
import javax.inject.Inject

@HomeFragmentScope
class ChannelsViewPagerAdapter @Inject constructor() :
    RecyclerView.Adapter<ChannelsViewPagerAdapter.MyViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ChannelsViewPagerAdapter.MyViewHolder {
        val binding =
            ChannelsViewPagerItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int = 3

    override fun onBindViewHolder(holder: ChannelsViewPagerAdapter.MyViewHolder, position: Int) {

    }

    class MyViewHolder(val binding: ChannelsViewPagerItemBinding) :
        RecyclerView.ViewHolder(binding.root)
}