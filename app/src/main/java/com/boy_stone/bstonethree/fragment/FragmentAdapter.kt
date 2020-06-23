package com.boy_stone.bstonethree.fragment

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class FragmentAdapter(
    fragmentManager: FragmentManager?,
    fragmentList: ArrayList<Fragment>
) :
    FragmentPagerAdapter(fragmentManager!!) {
    var fragmentList: ArrayList<Fragment> =
        ArrayList()

    override fun getItem(position: Int): Fragment {
        return fragmentList[position]
    }

    override fun getCount(): Int {
        return fragmentList.size
    }

    init {
        this.fragmentList = fragmentList
    }
}