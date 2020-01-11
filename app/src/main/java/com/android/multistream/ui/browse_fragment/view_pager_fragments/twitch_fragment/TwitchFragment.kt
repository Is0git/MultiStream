package com.android.multistream.ui.browse_fragment.view_pager_fragments.twitch_fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.android.multistream.databinding.TwitchGamesFragmentPageBinding
import com.android.multistream.util.ViewModelFactory
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class TwitchFragment : DaggerFragment() {

    lateinit var binding: TwitchGamesFragmentPageBinding
    @Inject lateinit var factory: ViewModelFactory
    lateinit var twitchFragmentViewModel: TwitchFragmentViewModel
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        twitchFragmentViewModel = ViewModelProviders.of(this, factory).get(TwitchFragmentViewModel::class.java)
        binding = TwitchGamesFragmentPageBinding.inflate(inflater, container, false)
      return  binding.root
    }
}