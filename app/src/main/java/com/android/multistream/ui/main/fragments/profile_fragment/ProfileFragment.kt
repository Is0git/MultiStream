package com.android.multistream.ui.main.fragments.profile_fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.android.multistream.databinding.ProfileLayoutBinding

class ProfileFragment : Fragment() {
    lateinit var binding: ProfileLayoutBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ProfileLayoutBinding.inflate(inflater, container, false)
        return binding.root
    }
}