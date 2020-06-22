package com.iso.multistream.ui.main_activity.fragments.game_category_fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.lifecycle.ViewModel
import androidx.lifecycle.observe
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.daggerviewmodelfragment.DaggerViewModelFragment
import com.example.multistreamaterialplaceholdercard.listeners.PlaceHolderViewListener
import com.example.overscrollbehavior.OverScrollBehavior
import com.example.pagination.PageLoader
import com.example.pagination.PageLoadingStates
import com.example.pagination.attach
import com.example.pagination.detach
import com.iso.multistream.R
import com.iso.multistream.databinding.GameCategoryLayoutBinding
import com.iso.multistream.databinding.ListItemTwoExtendedBinding
import com.iso.multistream.utils.PlaceHolderAdapter
import com.iso.multistream.utils.UIHelper

abstract class GameCategoryFragment<T, S : ViewModel>(clazz: Class<S>) : DaggerViewModelFragment<S>(clazz), UIHelper, OverScrollBehavior.OverScrollListener, PlaceHolderViewListener {

    lateinit var binding: GameCategoryLayoutBinding
    lateinit var adapter: PlaceHolderAdapter<T, ListItemTwoExtendedBinding>
    lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = GameCategoryLayoutBinding.inflate(inflater, container, false)
        ((binding.channelsCard.layoutParams as CoordinatorLayout.LayoutParams).behavior as OverScrollBehavior).overScrollListener = this
        binding.filledExposedDropdown.setAdapter(
            ArrayAdapter<String>(
                requireContext(),
                R.layout.drop_down_menu_item,
                Array(1) { getString(R.string.all) })
        )
        adapter = getPlaceHolderAdapter().also { it.setOnItemClickListener(this) }
        binding.channelsRecyclerview.adapter = adapter
        binding.channelsRecyclerview attach getPageLoader()
        navController = findNavController()
        observeData()
        setArgs()
        return binding.root
    }


    private fun invalidateList() {
        val pageLoader = getPageLoader()
        pageLoader.invalidate(true)
    }

    override fun onOverScrollCompleted() {
        invalidateList()
    }

    override fun onOverScrollStart() {

    }

    override fun observeData() {
        val pageLoader = getPageLoader()
        pageLoader.dataLiveData.observe(viewLifecycleOwner) {adapter.data = it}
        pageLoader.pageLoadingState.observe(viewLifecycleOwner) {
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
    }

    override fun onDestroyView() {
        binding.channelsRecyclerview detach getPageLoader()
        super.onDestroyView()
    }

    abstract fun setArgs()
    abstract fun getPlaceHolderAdapter() : PlaceHolderAdapter<T, ListItemTwoExtendedBinding>
    abstract fun getPageLoader() : PageLoader<T>
}