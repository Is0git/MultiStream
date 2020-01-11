package com.android.multistream.ui.browse_fragment.view_pager_fragments.mixer_fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import com.android.multistream.databinding.MixerGamesFragmentPagerBinding
import com.android.multistream.util.ViewModelFactory
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class MixerFragment : DaggerFragment(){

    lateinit var binding: MixerGamesFragmentPagerBinding
    @Inject lateinit var factory: ViewModelFactory
    lateinit var mixerFragmentViewModel: MixerFragmentViewModel
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mixerFragmentViewModel = ViewModelProviders.of(this, factory).get(MixerFragmentViewModel::class.java)
        binding = MixerGamesFragmentPagerBinding.inflate(inflater,container, false)
        return binding.root
    }
}