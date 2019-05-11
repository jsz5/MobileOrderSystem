package com.example.mobileordersystem.Utils

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter

class SectionsStatePagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {

    val mFragmantList = ArrayList<Fragment>()
    val mFragmentTitleList = ArrayList<String>()

    fun addFragment(fragment: Fragment, title: String) {
        mFragmantList.add(fragment)
        mFragmentTitleList.add(title)
    }

    override fun getItem(position: Int): Fragment {
        return  mFragmantList[position]
    }

    override fun getCount(): Int {
        return mFragmantList.size
    }


}