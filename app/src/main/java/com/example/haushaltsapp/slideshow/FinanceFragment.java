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

import com.example.haushaltsapp.R;

public class FinanceFragment extends Fragment {

    private FinanceViewModel financeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        financeViewModel = new ViewModelProvider(this).get(FinanceViewModel.class);
//                new ViewModelProvider(this).get(FinanceViewModel.class);
        View root = inflater.inflate(R.layout.fragment_finance, container, false);
        final TextView textView = root.findViewById(R.id.text_finance);
        financeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}