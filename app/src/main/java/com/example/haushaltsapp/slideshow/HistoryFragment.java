package com.example.haushaltsapp.slideshow;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.example.haushaltsapp.R;
import com.example.haushaltsapp.user.UserViewModel;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.firebase.auth.FirebaseAuth;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class HistoryFragment extends Fragment {
    private RecyclerView HistoryFragmentRecyclerView;
    private HistoryRecyclerViewAdapter historyRecyclerViewAdapter;
    private FinanceViewPageAdapter pagerAdapter;
    private ViewPager2 viewPager;
    private FinanceViewModel financeViewModel;
    private UserViewModel userViewModel;
    private Toolbar toolbar;
    private static final String TAG = HistoryFragment.class.getCanonicalName();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        financeViewModel = new ViewModelProvider(this).get(FinanceViewModel.class);
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);


    }
    public String getUid() {
        return FirebaseAuth.getInstance().getCurrentUser().getUid();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        String id = HistoryFragmentArgs.fromBundle(requireArguments()).getBudgetId();
        Bundle bundle = getArguments();
        financeViewModel.loadBudget(id);
        Log.i(TAG, "created HistoryFragment with id " + id);
        // Inflate the layout for this fragment

        View root = inflater.inflate(R.layout.fragment_history, container, false);
        toolbar = getActivity().findViewById(R.id.toolbar);

        financeViewModel.getBudgetsList().observe(getViewLifecycleOwner(), detail -> {
            Log.i(TAG, "Setting title to " + detail.getName());
            toolbar.setTitle(detail.getName());

        });
        return root;


    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {


        pagerAdapter = new FinanceViewPageAdapter(this);
        viewPager = view.findViewById(R.id.finance_detail_pager);
        viewPager.setAdapter(pagerAdapter);

        TabLayout tabLayout = view.findViewById(R.id.history_tab_layout);
        new TabLayoutMediator(tabLayout, viewPager,
                ((tab, position) -> tab.setIcon(FinanceViewPageAdapter.TAB_ICONS[position]))
        ).attach();








      /*  TabLayout tabLayout = getActivity().findViewById(R.id.tab);
        //  tabLayout.setupWithViewPager(viewPager);
        String [] tabTtiles={"Home","Chat","Notification","Account"};
        new TabLayoutMediator(tabLayout, viewPager,
                (tab, position) -> tab.setText(tabTtiles[position])
        ).attach();


*/


   /*     HistoryFragmentRecyclerView = view.findViewById(R.id.history_rv);
        HistoryFragmentRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        historyRecyclerViewAdapter = new HistoryRecyclerViewAdapter(getUid(),getActivity());
        HistoryFragmentRecyclerView.setAdapter(historyRecyclerViewAdapter);

        historyRecyclerViewAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {

            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                HistoryFragmentRecyclerView.smoothScrollToPosition(0);
            }



        });
    */
    }

}