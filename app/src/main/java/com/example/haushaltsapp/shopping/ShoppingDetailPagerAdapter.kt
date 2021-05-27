package com.example.haushaltsapp.shopping

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.haushaltsapp.R
import com.example.haushaltsapp.user.UserAddFragment
import com.example.haushaltsapp.user.UserFragment

class ShoppingDetailPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> ShoppingDetailEntriesFragment()
            1 -> UserFragment()
            2 -> UserAddFragment()
            else -> throw IllegalArgumentException("More entries in pager than expected")
        }
    }

    override fun getItemCount(): Int {
        return 3
    }

    companion object {
        val TAB_ICONS = intArrayOf(
            R.drawable.outline_shopping_cart_24,
            R.drawable.outline_group_24,
            R.drawable.outline_person_add_24
        )
    }
}