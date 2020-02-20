package com.android.multistream.ui.intro

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.android.multistream.databinding.IntroPageBinding
import dagger.android.support.DaggerFragment

class IntroPage : DaggerFragment(){
    lateinit var binding: IntroPageBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = IntroPageBinding.inflate(inflater, container, false)
        return binding.root
    }
}