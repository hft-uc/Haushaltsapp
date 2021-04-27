package com.example.haushaltsapp.shopping;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.haushaltsapp.R;
import com.example.haushaltsapp.user.UserAddFragment;
import com.example.haushaltsapp.user.UserFragment;

public class ShoppingDetailPagerAdapter extends FragmentStateAdapter {

    static final int[] TAB_ICONS = {
        R.drawable.outline_shopping_cart_24,
        R.drawable.outline_group_24,
        R.drawable.outline_person_add_24
    };

    public ShoppingDetailPagerAdapter(@NonNull Fragment fragment) {
        super(fragment);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new ShoppingDetailEntriesFragment();
            case 1:
                return new UserFragment();
            case 2:
                return new UserAddFragment();
            default:
                throw new IllegalArgumentException("More entries in pager than expected");
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
