package com.android.multistream.ui.intro.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.android.multistream.databinding.IntroPageBinding
import com.android.multistream.ui.main.activities.main_activity.MainActivity
import dagger.android.support.DaggerFragment

class IntroPage : DaggerFragment(){
    lateinit var binding: IntroPageBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = IntroPageBinding.inflate(inflater, container, false)
        (activity as MainActivity).hideActionBar()
        return binding.root
    }
}