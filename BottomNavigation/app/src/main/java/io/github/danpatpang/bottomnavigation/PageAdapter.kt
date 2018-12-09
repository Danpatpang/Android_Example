package io.github.danpatpang.bottomnavigation

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter

class PageAdapter(fm: FragmentManager, private val numOfTabs: Int) : FragmentStatePagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        when (position) {
            0 -> {
                val homeFragment = HomeFragment();
                return homeFragment;
            }

            1 -> {
                val analysisFragment = AnalysisFragment();
                return analysisFragment;
            }

            2 -> {
                val userFragment = UserFragment();
                return userFragment;
            }

            else -> {
                val homeFragment = HomeFragment();
                return homeFragment;
            }
        }
    }

    override fun getCount(): Int {
        return numOfTabs;
    }
}