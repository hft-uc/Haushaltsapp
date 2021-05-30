package com.example.haushaltsapp.shopping

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.example.haushaltsapp.R
import com.example.haushaltsapp.shopping.ShoppingViewModel
import com.example.haushaltsapp.types.ShoppingListDetail
import com.example.haushaltsapp.user.UserSource
import com.example.haushaltsapp.user.UserViewModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class ShoppingDetailFragment : Fragment() {

    private lateinit var toolbar: Toolbar
    private lateinit var pagerAdapter: ShoppingDetailPagerAdapter
    private lateinit var viewPager: ViewPager2
    private lateinit var shoppingViewModel: ShoppingViewModel
    private lateinit var userViewModel: UserViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        shoppingViewModel = ViewModelProvider(this).get(ShoppingViewModel::class.java)
        userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)
        val id = ShoppingDetailFragmentArgs.fromBundle(requireArguments()).shoppingId
        shoppingViewModel.loadShoppingList(id)
        Log.i(TAG, "created ShoppingDetailFragment with id $id")

        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val root = inflater.inflate(R.layout.fragment_shopping_detail, container, false)
        toolbar = requireActivity().findViewById(R.id.toolbar)
        shoppingViewModel.shoppingList.observe(viewLifecycleOwner,
            { detail: ShoppingListDetail ->
                Log.i(TAG, "Setting title to " + detail.name)
                toolbar.title = detail.name
                userViewModel.setSource(UserSource.SHOPPING)
                userViewModel.setId(detail.id)
                userViewModel.setShoppingListDetail(detail)
            })
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        pagerAdapter = ShoppingDetailPagerAdapter(this)
        viewPager = view.findViewById(R.id.shopping_detail_pager)
        viewPager.adapter = pagerAdapter

        val tabLayout: TabLayout = view.findViewById(R.id.shopping_detail_tab_layout)
        TabLayoutMediator(tabLayout, viewPager
        ) { tab, position ->
            tab.setIcon(ShoppingDetailPagerAdapter.TAB_ICONS[position])
        }
            .attach()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.shopping_detail, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (item.itemId == R.id.action_delete_shopping_list) {
            requireActivity().onBackPressed()
            shoppingViewModel.delete()
            true
        } else {
            false
        }

    }

    companion object {
        private val TAG = ShoppingDetailFragment::class.java.canonicalName
    }
}