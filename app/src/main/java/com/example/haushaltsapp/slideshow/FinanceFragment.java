package com.example.haushaltsapp.slideshow;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import com.example.haushaltsapp.R;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class FinanceFragment extends Fragment {


    public static FinanceFragment newInstance() {

        return new FinanceFragment();
    }

    private FinanceViewModel financeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        financeViewModel = new ViewModelProvider(this).get(FinanceViewModel.class);
//                new ViewModelProvider(this).get(FinanceViewModel.class);
        View root = inflater.inflate(R.layout.fragment_finance, container, false);
      //  final TextView textView = root.findViewById(R.id.text_finance);
        financeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
          //      textView.setText(s);
            }
        });


        return root;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ViewPager2 viewPager=  view.findViewById(R.id.pager);
        FinanceViewPageAdapter viewPagerAdapter = new FinanceViewPageAdapter(getActivity());
        viewPager.setAdapter(viewPagerAdapter);

        TabLayout tabLayout = getActivity().findViewById(R.id.tab);
        //  tabLayout.setupWithViewPager(viewPager);
        String [] tabTtiles={"Home","Chat","Notification","Account"};
        new TabLayoutMediator(tabLayout, viewPager,
                (tab, position) -> tab.setText(tabTtiles[position])
        ).attach();
    }


}