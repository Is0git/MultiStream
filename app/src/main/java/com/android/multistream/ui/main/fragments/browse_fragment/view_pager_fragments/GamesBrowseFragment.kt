package com.android.multistream.ui.main.fragments.browse_fragment.view_pager_fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.observe
import androidx.recyclerview.widget.GridLayoutManager
import com.android.multistream.databinding.TopGamesFragmentPageBinding
import com.android.multistream.utils.UIHelper
import com.example.daggerviewmodelfragment.DaggerViewModelFragment
import com.example.pagination.PageLoader
import com.example.pagination.PageLoadingStates
import com.example.pagination.attach
import com.example.pagination.detach
import com.multistream.multistreamsearchview.search_view.OnItemClickListener
import com.multistream.multistreamsearchview.search_view.convertDpToPixel

abstract class GamesBrowseFragment<T : ViewModel>(clazz: Class<T>) :
    DaggerViewModelFragment<T>(clazz), OnItemClickListener, UIHelper {

    lateinit var binding: TopGamesFragmentPageBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = TopGamesFragmentPageBinding.inflate(inflater, container, false)
        binding.topGamesList attach getPageLoader()
        setItemCount()
        val stateLiveData = getPageLoadStateLiveData()
        stateLiveData.observe(viewLifecycleOwner) {
            binding.progressBar.visibility = when (it) {
                PageLoadingStates.START -> View.VISIBLE
                PageLoadingStates.FAILED -> {
                    Toast.makeText(
                        requireContext(),
                        "FAILED TO LOAD PAGED DATA",
                        Toast.LENGTH_SHORT
                    ).show()
                    View.INVISIBLE
                }
                PageLoadingStates.LOADING -> View.VISIBLE
                PageLoadingStates.SUCCESS -> View.INVISIBLE
                else -> View.INVISIBLE
            }
        }
        observeData()
        return binding.root
    }

    private fun setItemCount() {
        (binding.topGamesList.layoutManager as GridLayoutManager).apply {
//            spanCount = resources.displayMetrics.widthPixels / convertDpToPixel(138, resources)
        }
    }

    override fun onDestroy() {
        binding.topGamesList
        binding.topGamesList detach getPageLoader()
        super.onDestroy()
    }

    abstract fun getPageLoader(): PageLoader<*>
    abstract fun getPageLoadStateLiveData(): LiveData<PageLoadingStates>

}