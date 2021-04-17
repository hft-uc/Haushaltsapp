package com.example.haushaltsapp.shopping;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.haushaltsapp.R;
import com.example.haushaltsapp.user.UserFragment;

public class ShoppingDetailPagerAdapter extends FragmentStateAdapter {

    static final int[] TAB_ICONS = {R.drawable.outline_shopping_cart_24, R.drawable.outline_group_24};

    public ShoppingDetailPagerAdapter(@NonNull Fragment fragment) {
        super(fragment);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Fragment fragment = new UserFragment();
        return fragment;
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
