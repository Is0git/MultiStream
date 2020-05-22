package com.android.multistream.ui.main.fragments.profile_fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.android.multistream.databinding.ProfileLayoutBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.profile_content.view.*
import javax.inject.Inject

abstract class ProfileFragment : DaggerFragment(), View.OnClickListener {

    lateinit var binding: ProfileLayoutBinding
    var channelId: String? = null
    lateinit var navController: NavController


    @Inject
    lateinit var viewPagerAdapter: ProfileViewPagerAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ProfileLayoutBinding.inflate(inflater, container, false)
        channelId = getChannelIdFromArgs()
        setupViewPager()
        resolveArgs()
        navController = findNavController()
        binding.profileLayout.apply {
            back.setOnClickListener(this@ProfileFragment)
            back_button.setOnClickListener(this@ProfileFragment)

        }
        binding.profileLayout.follow_button.setOnClickListener {
            it.isSelected = !it.isSelected
        }
        return binding.root
    }

    private fun setupViewPager() {
        viewPagerAdapter = ProfileViewPagerAdapter(childFragmentManager, lifecycle)
        binding.profileViewPager.adapter = viewPagerAdapter
        addViewPagerFragments()
        TabLayoutMediator(
            binding.profileTabLayout,
            binding.profileViewPager,
            true
        ) { tab: TabLayout.Tab, position: Int ->
            tab.text = viewPagerAdapter.getFragmentTitle(position)
        }.attach()
    }

    override fun onClick(v: View?) {
        navController.navigateUp()
    }

    fun createProfileFragment(title: String, fragmentClass: Class<out Fragment>, id: String?) {
       val fragment = fragmentClass.newInstance().apply {
           this.arguments = Bundle().also {
               it.putString("channelId", id)
           }
       }
        viewPagerAdapter.addFragment(title, fragment)
    }

    abstract fun addViewPagerFragments()
    abstract fun resolveArgs()
    abstract fun getChannelIdFromArgs() : String?
}