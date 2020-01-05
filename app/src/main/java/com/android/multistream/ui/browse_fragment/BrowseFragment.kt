package com.android.multistream.ui.browse_fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import com.android.multistream.databinding.BrowseFragmentBinding
import com.android.multistream.util.ViewModelFactory
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class BrowseFragment : DaggerFragment() {

    @Inject lateinit var factory: ViewModelFactory
    @Inject lateinit var topGamesAdapter: TopGamesListAdapter
    lateinit var browseViewModel: BrowseFragmentViewModel

    lateinit var binding: BrowseFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        browseViewModel = ViewModelProviders.of(this, factory).get(BrowseFragmentViewModel::class.java)
        binding = BrowseFragmentBinding.inflate(inflater, container, false)

        binding.apply {
            topGamesList.adapter = topGamesAdapter
        }

        browseViewModel.topGamesPagedListLiveData.observe(viewLifecycleOwner, Observer { topGamesAdapter.submitList(it) })
        return binding.root
    }
}