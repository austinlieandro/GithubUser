package com.example.austinlieandro_navigationdanapi.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class SectionsPagerAdapter(activity: AppCompatActivity, private val username: String): FragmentStateAdapter(activity) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when(position){
            0 -> {
                fragment = FollowersFragment()
                fragment.arguments = Bundle().apply {
                    putInt(FollowersFragment.ARG_SECTION_NUMBER, position + 1)
                    putString(FollowersFragment.EXTRA_USER, username)
                }
            }
            1 ->{
                fragment = FollowingFragment()
                fragment.arguments = Bundle().apply {
                    putInt(FollowingFragment.ARG_SECTION_NUMBER, position + 1)
                    putString(FollowingFragment.EXTRA_USER, username)
                }
            }
        }
        return fragment as Fragment
    }
}