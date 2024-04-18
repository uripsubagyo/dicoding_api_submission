package com.example.submitiondicoding2.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class SectionPagerAdapter(activity:AppCompatActivity) : FragmentStateAdapter(activity) {

    lateinit var USERNAME:String

    override fun getItemCount():Int {
        return 2;
    }

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        var bundle = Bundle()
        bundle.putString("USERNAME", USERNAME)

        if(USERNAME!= null){
            when (position) {
                0 -> {
                    fragment = FollowingFragment()
                    fragment.arguments = bundle
                }
                1 -> {
                    fragment = FollowersFragment()
                    fragment.arguments = bundle
                }
            }
        }

        return fragment as Fragment
    }

}