package com.example.haushaltsapp.slideshow;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.haushaltsapp.R;

public class FinanceViewPageAdapter extends FragmentStateAdapter {
    static final int[] TAB_ICONS = {
            R.drawable.outline_shopping_cart_24,
            R.drawable.outline_group_24,
            R.drawable.outline_person_add_24
    };

    public FinanceViewPageAdapter(@NonNull Fragment fa) {
        super(fa);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {


        switch (position) {
            case 0:
                return new GraphicFragment();
            case 1:
                return new HistoryListFragment();
        }
        return null;

    }

    @Override
    public int getItemCount() {
        return 2;
    }


}
