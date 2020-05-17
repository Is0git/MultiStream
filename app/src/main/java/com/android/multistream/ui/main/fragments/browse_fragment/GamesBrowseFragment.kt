package com.android.multistream.ui.main.fragments.browse_fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModel
import com.android.multistream.databinding.TopGamesFragmentPageBinding
import com.example.daggerviewmodelfragment.DaggerViewModelFragment
import com.example.pagination.PageLoader
import com.example.pagination.attach
import com.example.pagination.detach

abstract class GamesBrowseFragment<T : ViewModel>(clazz: Class<T>) :
    DaggerViewModelFragment<T>(clazz) {

    lateinit var binding: TopGamesFragmentPageBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = TopGamesFragmentPageBinding.inflate(inflater, container, false)
        binding.topGamesList attach getPageLoader()
        observe()
        return binding.root
    }

    override fun onDestroy() {
        binding.topGamesList
        binding.topGamesList detach getPageLoader()
        super.onDestroy()
    }

    abstract fun getPageLoader(): PageLoader<*>

    abstract fun observe()

}