package com.iso.multistream.ui.main_activity.fragments.profile_fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.observe
import com.example.daggerviewmodelfragment.DaggerViewModelFragment
import com.example.pagination.PageLoader
import com.example.pagination.PageLoadingStates
import com.example.pagination.attach
import com.example.pagination.detach
import com.iso.multistream.R
import com.iso.multistream.databinding.PastVideosFragmentLayoutBinding
import com.iso.multistream.ui.main_activity.fragments.profile_fragment.twitch_profile.past_recordings.PastStreamAdapter
import com.iso.multistream.utils.UIHelper
import com.multistream.multistreamsearchview.search_view.OnItemClickListener
import kotlinx.android.synthetic.main.past_videos_pickers_layout.view.*
import javax.inject.Inject

abstract class RecordingFragment<T : ViewModel, S>(clazz: Class<T>) :
    DaggerViewModelFragment<T>(clazz), UIHelper, AdapterView.OnItemClickListener, OnItemClickListener {

    lateinit var binding: PastVideosFragmentLayoutBinding
    protected var pickersList = mutableListOf<Map<String, String>>()
    @Inject
    lateinit var videoListAdapter: PastStreamAdapter<S>
    var channelId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        channelId = arguments?.getString("channelId")
        setId(channelId)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = PastVideosFragmentLayoutBinding.inflate(inflater, container, false)
        setPickers()
        setupAdapter()
        getPageLoader().loadInit()
        observeData()
        return binding.root
    }

    private fun setPickers() {
        binding.pickerLayout.apply {
            getCategoriesMap().also {
                if (it != null) createPickerItem(
                    it,
                    category_drop_down
                ) else categoryPicker.visibility = View.GONE
            }
            getSortMap().also {
                if (it != null) createPickerItem(it, sort_drop_down) else sortPicker.visibility = View.GONE
            }
            getPeriodMap().also {
                if (it != null) createPickerItem(
                    it,
                    period_drop_down
                ) else periodPicker.visibility = View.GONE
            }
        }
    }

    private fun setupAdapter() {
        binding.videosList.apply {
            videoListAdapter.onBind = onBind()
            videoListAdapter.onItemClickListener = this@RecordingFragment
            adapter = videoListAdapter
            this attach getPageLoader()
        }
    }

    @Suppress("UNCHECKED_CAST")
    override fun observeData() {
        getPageLoader().apply {
            dataLiveData.observe(viewLifecycleOwner) {
                binding.noItem.visibility = if (it != null && it.isEmpty()) {
                    View.VISIBLE
                } else View.INVISIBLE
                videoListAdapter.data = it
            }
            pageLoadingState.observe(viewLifecycleOwner) {
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
    }

    private fun createPickerItem(
        map: Map<String, String>,
        view: AutoCompleteTextView,
        isClickable: Boolean = true
    ) {
        pickersList.add(map)
        view.setAdapter(
            createPickerAdapter(map.keys.toTypedArray())
        )
        view.onItemSelectedListener
        if (isClickable) view.onItemClickListener = this
    }

    private fun createPickerAdapter(array: Array<String>) = run {
        ArrayAdapter(
            requireContext(),
            R.layout.drop_down_menu_item,
            array
        )
    }

    abstract fun onBind(): (holder: PastStreamAdapter.VideosViewHolder, position: Int, item: S) -> Unit
    abstract fun getCategoriesMap(): Map<String, String>?
    abstract fun getSortMap(): Map<String, String>?
    abstract fun getPeriodMap(): Map<String, String>?
    abstract fun getPageLoader(): PageLoader<S>
    abstract fun setId(id: String?)

    override fun onDestroy() {
        super.onDestroy()
        binding.videosList detach getPageLoader()
    }

    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        getPageLoader().invalidate(true)
    }

    data class PickerItem(var key: String?, var visibleName: String)
}