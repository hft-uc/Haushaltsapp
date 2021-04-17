package com.example.haushaltsapp.shopping;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import com.example.haushaltsapp.R;
import com.example.haushaltsapp.user.UserSource;
import com.example.haushaltsapp.user.UserViewModel;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class ShoppingDetailFragment extends Fragment {
    private static final String TAG = ShoppingDetailFragment.class.getCanonicalName();

    private Toolbar toolbar;

    private ShoppingDetailPagerAdapter pagerAdapter;
    private ViewPager2 viewPager;
    private ShoppingViewModel shoppingViewModel;
    private UserViewModel userViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        shoppingViewModel = new ViewModelProvider(this).get(ShoppingViewModel.class);
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);

        String id = ShoppingDetailFragmentArgs.fromBundle(requireArguments()).getShoppingId();
        shoppingViewModel.loadShoppingList(id);

        Log.i(TAG, "created ShoppingDetailFragment with id " + id);
    }

    @Override
    public View onCreateView(
        @NonNull LayoutInflater inflater,
        @Nullable ViewGroup container,
        @Nullable Bundle savedInstanceState
    ) {
        View root = inflater.inflate(R.layout.fragment_shopping_detail, container, false);
        toolbar = getActivity().findViewById(R.id.toolbar);

        shoppingViewModel.getShoppingList().observe(getViewLifecycleOwner(), detail -> {
            Log.i(TAG, "Setting title to " + detail.getName());
            toolbar.setTitle(detail.getName());

            userViewModel.setSource(UserSource.SHOPPING);
            userViewModel.setId(detail.getId());

        });


        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        pagerAdapter = new ShoppingDetailPagerAdapter(this);
        viewPager = view.findViewById(R.id.shopping_detail_pager);
        viewPager.setAdapter(pagerAdapter);

        TabLayout tabLayout = view.findViewById(R.id.shopping_detail_tab_layout);
        new TabLayoutMediator(tabLayout, viewPager,
            ((tab, position) -> tab.setIcon(ShoppingDetailPagerAdapter.TAB_ICONS[position]))
        ).attach();
    }
}
