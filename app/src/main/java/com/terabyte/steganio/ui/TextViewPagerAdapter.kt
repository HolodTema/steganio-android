package com.terabyte.steganio.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.terabyte.steganio.fragment.TextDecryptFragment
import com.terabyte.steganio.fragment.TextEncryptFragment

class TextViewPagerAdapter(
    fragmentActivity: FragmentActivity,
    private val listTabTitles: List<String>
): FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int {
        return listTabTitles.size
    }

    override fun createFragment(position: Int): Fragment {
        when(position) {
            0 -> return TextEncryptFragment()
            1 -> return TextDecryptFragment()
        }
        return TextEncryptFragment()
    }
}