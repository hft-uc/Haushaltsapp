package com.example.haushaltsapp.slideshow;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.haushaltsapp.R;


public class HistoryListFragment extends Fragment {

    private FinanceViewModel financeViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        financeViewModel = new ViewModelProvider(requireParentFragment()).get(FinanceViewModel.class);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history_list, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.HistoryListRec);

        final HistoryRecyclerViewAdapter adapter = new HistoryRecyclerViewAdapter(financeViewModel.createEntriesAdapter(getViewLifecycleOwner()));
        recyclerView.setAdapter(adapter);
        // Inflate the layout for this fragment
        return view;
    }
}