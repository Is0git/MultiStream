package com.android.multistream.ui.intro.fragments

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.get
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.android.multistream.R
import com.android.multistream.databinding.IntroPageBinding
import com.android.multistream.ui.main.activities.main_activity.MainActivity
import com.android.multistream.utils.ViewModelFactory
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class IntroPage : DaggerFragment(){
    lateinit var binding: IntroPageBinding

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    lateinit var introViewModel: IntroViewModel

    lateinit var nav: NavController

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = IntroPageBinding.inflate(inflater, container, false)
        (activity as MainActivity).hideActionBar()

        introViewModel = ViewModelProviders.of(this, viewModelFactory).get(IntroViewModel::class.java)

        binding.apply {
            continueButton.setOnClickListener { nav.navigate(R.id.action_introPage_to_introPageTwo) }
            skipButton.setOnClickListener { nav.navigate(R.id.action_global_main) }
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        nav = findNavController()
    }

}