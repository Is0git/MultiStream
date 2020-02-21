package com.android.multistream.ui.intro.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import com.android.multistream.databinding.IntroPageTwoBinding
import com.android.multistream.utils.twitchAPI.twitchAuthPage
import dagger.android.support.DaggerFragment

class IntroPageTwo : DaggerFragment(){
    lateinit var binding: IntroPageTwoBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = IntroPageTwoBinding.inflate(inflater, container, false)
        binding.button.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, twitchAuthPage.toUri())
            startActivity(intent)
        }
        return binding.root
    }
}