package com.android.multistream.ui.main.fragments.game_category_fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.android.multistream.R
import com.android.multistream.databinding.GameCategoryLayoutBinding
import com.android.multistream.databinding.ListItemTwoExtendedBinding
import com.android.multistream.network.twitch.models.v5.followed_streams.StreamsItem
import com.android.multistream.utils.PlaceHolderAdapter
import com.android.multistream.utils.UIHelper
import com.android.multistream.utils.data_binding.ImageLoader
import com.example.daggerviewmodelfragment.DaggerViewModelFragment
import com.example.pagination.PageLoader
import com.example.pagination.attach
import com.multistream.multistreamsearchview.search_view.OnItemClickListener

abstract class GameCategoryFragment<T, S : ViewModel>(clazz: Class<S>) : DaggerViewModelFragment<S>(clazz), UIHelper {

    lateinit var binding: GameCategoryLayoutBinding
    lateinit var adapter: PlaceHolderAdapter<T, ListItemTwoExtendedBinding>
    lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = GameCategoryLayoutBinding.inflate(inflater, container, false)
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
        observe()
        return binding.root
    }

    abstract fun getPlaceHolderAdapter() : PlaceHolderAdapter<T, ListItemTwoExtendedBinding>
    abstract fun onCardClick(position: Int, view: View)
    abstract fun getPageLoader() : PageLoader<*>
}