package com.android.multistream.ui.home_fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import com.android.multistream.databinding.HomeFragmentBinding
import com.android.multistream.util.ViewModelFactory
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class HomeFragment : DaggerFragment() {
    lateinit var binding: HomeFragmentBinding
    @Inject lateinit var factory: ViewModelFactory
    lateinit var viewModel: HomeFragmentViewModel
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = HomeFragmentBinding.inflate(inflater, container, false)
        viewModel = ViewModelProviders.of(this, factory).get(HomeFragmentViewModel::class.java)
        return binding.root
    }
}