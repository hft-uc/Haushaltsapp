package com.example.haushaltsapp.slideshow;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.haushaltsapp.shopping.ShoppingDetailFragment;
import com.example.haushaltsapp.slideshow.FinanceFragment;

public class FinanceViewPageAdapter extends FragmentStateAdapter {

    public FinanceViewPageAdapter(@NonNull FragmentActivity fa) {
        super(fa);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {


        switch (position) {
            case 0:
                return HistoryFragment.newInstance();

        }
        return null;



    }

    @Override
    public int getItemCount() {
        return 1;
    }


}
