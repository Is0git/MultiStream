package com.android.multistream.ui.browse_fragment.view_pager_fragments.mixer_fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.android.multistream.databinding.MixerGamesFragmentPagerBinding
import dagger.android.support.DaggerFragment

class MixerFragment : DaggerFragment(){

    lateinit var binding: MixerGamesFragmentPagerBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = MixerGamesFragmentPagerBinding.inflate(inflater,container, false)
        return binding.root
    }
}