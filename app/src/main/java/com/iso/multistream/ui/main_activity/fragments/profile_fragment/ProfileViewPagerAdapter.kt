package com.iso.multistream.ui.main_activity.fragments.profile_fragment

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.iso.multistream.di.main_activity.main_fragments.profile_fragments.twitch_profile_fragment.ProfileFragmentScope
import javax.inject.Inject

@ProfileFragmentScope
class ProfileViewPagerAdapter @Inject constructor(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    private val listOfFragment: MutableList<ViewPagerFragmentData> by lazy { mutableListOf<ViewPagerFragmentData>() }

    override fun getItemCount(): Int = listOfFragment.count()

    override fun createFragment(position: Int): Fragment {
        val fragmentData = listOfFragment[position]
        return  when {
            fragmentData.fragmentClass != null -> fragmentData.fragmentClass!!.newInstance()
            fragmentData.fragment != null -> fragmentData.fragment as Fragment
            else -> Fragment()
        }
    }

    fun addFragment(title: String, fragmentClass: Class<out Fragment>) {
        ViewPagerFragmentData(title, fragmentClass).also { listOfFragment.add(it) }
    }

    fun addFragment(title: String, fragment: Fragment) {
        ViewPagerFragmentData(title, null, fragment).also { listOfFragment.add(it) }
    }

    fun getFragmentTitle(position: Int): String = listOfFragment[position].title

    class ViewPagerFragmentData(var title: String, var fragmentClass: Class<out Fragment>? = null, var fragment: Fragment? = null)
}