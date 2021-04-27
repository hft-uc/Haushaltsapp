package com.example.haushaltsapp.slideshow;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.haushaltsapp.R;
import com.google.firebase.auth.FirebaseAuth;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HistoryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HistoryFragment extends Fragment {
    private RecyclerView HistoryFragmentRecyclerView;
    private HistoryRecyclerViewAdapter historyRecyclerViewAdapter;
    public static HistoryFragment newInstance() {

        return new HistoryFragment();
    }


    public HistoryFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    public String getUid() {
        return FirebaseAuth.getInstance().getCurrentUser().getUid();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_history, container, false);
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        HistoryFragmentRecyclerView = view.findViewById(R.id.history_rv);
        HistoryFragmentRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        historyRecyclerViewAdapter = new HistoryRecyclerViewAdapter(getUid(),getActivity());
        HistoryFragmentRecyclerView.setAdapter(historyRecyclerViewAdapter);

        historyRecyclerViewAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {

            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                HistoryFragmentRecyclerView.smoothScrollToPosition(0);
            }
        });
    }
}