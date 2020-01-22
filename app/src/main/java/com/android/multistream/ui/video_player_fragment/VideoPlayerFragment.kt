package com.android.multistream.ui.video_player_fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.android.multistream.databinding.StreamPlayerFragmentBinding

class VideoPlayerFragment : Fragment(){
    lateinit var binding: StreamPlayerFragmentBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = StreamPlayerFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }
}