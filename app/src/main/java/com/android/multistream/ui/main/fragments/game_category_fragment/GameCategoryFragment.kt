package com.android.multistream.ui.main.fragments.game_category_fragment

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
import com.android.multistream.R
import com.android.multistream.databinding.GameCategoryLayoutBinding
import com.android.multistream.databinding.ListItemTwoExtendedBinding
import com.android.multistream.utils.PlaceHolderAdapter
import com.android.multistream.utils.UIHelper
import com.example.daggerviewmodelfragment.DaggerViewModelFragment
import com.example.overscrollbehavior.OverScrollBehavior
import com.example.pagination.PageLoader
import com.example.pagination.PageLoadingStates
import com.example.pagination.attach

abstract class GameCategoryFragment<T, S : ViewModel>(clazz: Class<S>) : DaggerViewModelFragment<S>(clazz), UIHelper, OverScrollBehavior.OverScrollListener {

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
                Array(2) { "Hi" })
        )
        adapter = getPlaceHolderAdapter().also { it.setOnItemClickListener { position, itemView -> onCardClick(position, itemView) } }
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

    abstract fun setArgs()
    abstract fun getPlaceHolderAdapter() : PlaceHolderAdapter<T, ListItemTwoExtendedBinding>
    abstract fun onCardClick(position: Int, view: View)
    abstract fun getPageLoader() : PageLoader<T>
}