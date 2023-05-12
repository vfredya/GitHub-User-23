package com.example.githubuser.adapter

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.githubuser.fragment.FollowersFragment
import com.example.githubuser.fragment.FollowingFragment

class SectionPagerAdapter(activity: AppCompatActivity, private val login: String?) :
    FragmentStateAdapter(activity) {


    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> {
                fragment = FollowersFragment()
                login?.let {
                    (fragment as FollowersFragment).arguments = Bundle().apply {
                        putString(FollowersFragment.ARG_USERNAME, it)
                    }
                }
            }

            1 -> {
                fragment = FollowingFragment()
                login?.let {
                    (fragment as FollowingFragment).arguments = Bundle().apply {
                        putString(FollowingFragment.ARG_USERNAME, it)
                    }
                }
            }
        }
        return fragment as Fragment
    }

    override fun getItemCount()
            : Int {
        return 2
    }
}