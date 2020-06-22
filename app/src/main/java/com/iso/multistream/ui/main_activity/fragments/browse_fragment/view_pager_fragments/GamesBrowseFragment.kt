package com.iso.multistream.ui.main_activity.fragments.browse_fragment.view_pager_fragments

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.observe
import androidx.recyclerview.widget.GridLayoutManager
import com.example.daggerviewmodelfragment.DaggerViewModelFragment
import com.example.pagination.PageLoader
import com.example.pagination.PageLoadingStates
import com.example.pagination.attach
import com.example.pagination.detach
import com.iso.multistream.databinding.TopGamesFragmentPageBinding
import com.iso.multistream.utils.UIHelper
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
        (binding.topGamesList.layoutManager as GridLayoutManager).spanSizeLookup
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
            spanCount =
                if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) 3 else {
                    3.coerceAtLeast(
                        resources.displayMetrics.widthPixels / convertDpToPixel(
                            110,
                            resources
                        )
                    )
                }
        }
    }

    override fun onDestroyView() {
        binding.topGamesList
        binding.topGamesList detach getPageLoader()
        super.onDestroyView()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
       setItemCount()
    }

    abstract fun getPageLoader(): PageLoader<*>
    abstract fun getPageLoadStateLiveData(): LiveData<PageLoadingStates>

}