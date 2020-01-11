package com.android.multistream.ui.browse_fragment.view_pager_fragments.top_fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.android.multistream.databinding.GamesTopFragmentPageBinding
import dagger.android.support.DaggerFragment

class TopGamesFragment : DaggerFragment() {
    lateinit var binding: GamesTopFragmentPageBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = GamesTopFragmentPageBinding.inflate(inflater, container, false)
        return binding.root
    }
}