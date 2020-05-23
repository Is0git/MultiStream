package com.android.multistream.ui.main.fragments.profile_fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.android.multistream.databinding.ProfileLayoutBinding
import com.android.multistream.ui.main.fragments.profile_fragment.twitch_profile.TwitchProfileViewModel
import com.android.multistream.ui.main_activity.MainActivityViewModel
import com.android.multistream.ui.main_activity.clicks
import com.example.daggerviewmodelfragment.DaggerViewModelFragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.profile_content.view.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

abstract class ProfileFragment<T : ViewModel>(clazz: Class<T>) : DaggerViewModelFragment<T>(clazz),
    View.OnClickListener {

    lateinit var binding: ProfileLayoutBinding
    var channelId: String? = null
    lateinit var navController: NavController
    lateinit var mainActivityViewModel: MainActivityViewModel


    @Inject
    lateinit var viewPagerAdapter: ProfileViewPagerAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ProfileLayoutBinding.inflate(inflater, container, false)
        channelId = getChannelIdFromArgs()
        observeFollowing()
        setupViewPager()
        resolveArgs()
        navController = findNavController()
        mainActivityViewModel = ViewModelProvider(requireActivity()).get(MainActivityViewModel::class.java)
        binding.profileLayout.apply {
            back.setOnClickListener(this@ProfileFragment)
            back_button.setOnClickListener(this@ProfileFragment)
        }
        binding.profileLayout.follow_button.also { view ->
            view.clicks()
                .buffer(Channel.CONFLATED)
                .onEach {
                    view.isSelected = !view.isSelected
                    onFollowClick(view)
                }
                .launchIn(lifecycleScope)
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

    protected fun isFollowing(isFollowing: Boolean) {
        binding.profileLayout.follow_button.isSelected = isFollowing
    }

    abstract suspend fun onFollowClick(view: View)
    abstract fun observeFollowing()
    abstract fun addViewPagerFragments()
    abstract fun resolveArgs()
    abstract fun getChannelIdFromArgs(): String
}