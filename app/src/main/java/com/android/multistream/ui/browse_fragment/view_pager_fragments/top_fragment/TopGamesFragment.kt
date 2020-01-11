package com.android.multistream.ui.browse_fragment.view_pager_fragments.top_fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import com.android.multistream.databinding.GamesTopFragmentPageBinding
import com.android.multistream.util.ViewModelFactory
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class TopGamesFragment : DaggerFragment() {
    lateinit var binding: GamesTopFragmentPageBinding
    @Inject lateinit var factory: ViewModelFactory
    lateinit var topFragmentViewModel: TopFragmentViewModel
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        topFragmentViewModel = ViewModelProviders.of(this, factory).get(TopFragmentViewModel::class.java)
        binding = GamesTopFragmentPageBinding.inflate(inflater, container, false)
        return binding.root
    }
}