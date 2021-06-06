package com.example.haushaltsapp.slideshow;

import android.app.AlertDialog;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.haushaltsapp.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class FinanceFragment extends Fragment {

    FloatingActionButton action;
    private FinanceViewModel financeViewModel;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_finance, container, false);
        financeViewModel = new ViewModelProvider(this).get(FinanceViewModel.class);

        RecyclerView recyclerView = root.findViewById(R.id.BudgetRec);
        recyclerView.setAdapter(financeViewModel.createBudgetAdapter(getViewLifecycleOwner()));

        action = root.findViewById(R.id.button_temp);

        action.setOnClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setTitle("Neues Budget erstellen");
            // Set up the input
            final EditText input = new EditText(getContext());
            input.setHint("");
            input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_WORDS);
            builder.setView(input);

            builder.setPositiveButton("OK", (dialog, which) -> {
                String text = input.getText().toString();

                financeViewModel.add(text);
            });
            builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());

            builder.show();
        });


        return root;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }


}