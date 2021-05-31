package com.example.haushaltsapp.slideshow;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.haushaltsapp.R;
import com.example.haushaltsapp.user.UserAddFragment;
import com.example.haushaltsapp.user.UserFragment;

public class FinanceViewPageAdapter extends FragmentStateAdapter {
    static final int[] TAB_ICONS = {
            R.drawable.outline_shopping_cart_24,
            R.drawable.ic_menu_camera,
            R.drawable.outline_person_add_24,
            R.drawable.outline_group_24,


    };

    public FinanceViewPageAdapter(@NonNull Fragment fa) {
        super(fa);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {

        switch (position) {
            case 0:
                return new HistoryListFragment();
            case 1:
                return new GraphicFragment();
            case 2:
                return new UserAddFragment();
            case 3:
                return new UserFragment();
        }
        return null;
    }

    @Override
    public int getItemCount() {
        return 4;
    }


}
