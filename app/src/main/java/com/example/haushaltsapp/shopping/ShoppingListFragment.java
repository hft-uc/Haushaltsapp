package com.example.haushaltsapp.shopping;

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

public class ShoppingListFragment extends Fragment {
    private ShoppingViewModel shoppingViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_shopping_list, container, false);
        shoppingViewModel =
            new ViewModelProvider(this).get(ShoppingViewModel.class);

        RecyclerView recyclerView = root.findViewById(R.id.shopping_list);
        recyclerView.setAdapter(shoppingViewModel.createShoppingListAdapter(getViewLifecycleOwner()));

        root.<FloatingActionButton>findViewById(R.id.add_shopping_list_fab).setOnClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setTitle("Neue Liste erstellen");
            // Set up the input
            final EditText input = new EditText(getContext());
            input.setHint("blabla");
            input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_WORDS);
            builder.setView(input);

            builder.setPositiveButton("OK", (dialog, which) -> {
                String text = input.getText().toString();

                shoppingViewModel.add(text);
            });
            builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());

            builder.show();
        });

        return root;
    }
}