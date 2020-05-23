package com.android.multistream.ui.main.fragments.view_all_fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.observe
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.android.multistream.databinding.ViewAllLayoutBinding
import com.android.multistream.utils.UIHelper
import com.example.daggerviewmodelfragment.DaggerViewModelFragment
import com.example.pagination.PageLoader
import com.example.pagination.PageLoadingStates
import com.example.pagination.attach
import com.example.pagination.detach
import com.multistream.multistreamsearchview.search_view.OnItemClickListener
import kotlinx.android.synthetic.main.view_all_app_bar.view.*

abstract class ViewAllFragment<T : ViewModel, S>(clazz: Class<T>) :
    DaggerViewModelFragment<T>(clazz), UIHelper, OnItemClickListener, View.OnClickListener {

    protected lateinit var viewAllListAdapter: ViewAllListAdapter<S>
    lateinit var binding: ViewAllLayoutBinding
    lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ViewAllLayoutBinding.inflate(inflater, container, false)
        setAdapter()
        observeData()
        getPageLoader().loadInit()
        binding.viewAppBar.apply {
            arrow_view.setOnClickListener(this@ViewAllFragment)
            title.setOnClickListener(this@ViewAllFragment)
        }
        binding.viewAppBar.title.text = getHeaderText()
        return binding.root
    }

    private fun setAdapter() {
        viewAllListAdapter = ViewAllListAdapter<S>(getListType()).apply {
            this.onBind = onBind()
            this.onItemClickListener = this@ViewAllFragment
        }
        binding.viewAllList.adapter = viewAllListAdapter
        binding.viewAllList attach getPageLoader()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        navController = Navigation.findNavController(view)
    }

    override fun observeData() {
        val pageLoader = getPageLoader()
        pageLoader.dataLiveData.observe(viewLifecycleOwner) {
            viewAllListAdapter.data = it
        }
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

    override fun onClick(v: View?) {
        navController.navigateUp()
    }

    override fun onDestroyView() {
        binding.viewAllList detach getPageLoader()
        super.onDestroyView()
    }

    protected abstract fun getHeaderText() : CharSequence
    protected abstract fun onBind(): (ViewAllListAdapter.SearchViewHolder, S?, Int) -> Unit
    protected abstract fun getPageLoader(): PageLoader<S>
    protected abstract fun getListType(): Int
}