package com.example.haushaltsapp.shopping;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.haushaltsapp.R;
import com.google.android.material.textfield.TextInputEditText;

/**
 * A fragment representing a list of Items.
 */
public class ShoppingDetailEntriesFragment extends Fragment {

    private ShoppingViewModel shoppingViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        shoppingViewModel = new ViewModelProvider(requireParentFragment()).get(ShoppingViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shopping_detail_entries_list, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.shopping_detail_entry_list);

        final ShoppingDetailEntriesRecyclerViewAdapter adapter = new ShoppingDetailEntriesRecyclerViewAdapter(
            shoppingViewModel.createShoppingListEntriesAdapter(getViewLifecycleOwner()),
            item -> shoppingViewModel.toggleDone(item));
        recyclerView.setAdapter(adapter);

        initAddButton(view);

        return view;
    }

    private void initAddButton(View view) {
        view.findViewById(R.id.add_shopping_list_entry_fab)
            .setOnClickListener(v -> {
                final Context context = getContext();

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle(R.string.add_entry);
                final LinearLayout layout = new LinearLayout(context);
                layout.setOrientation(LinearLayout.VERTICAL);

                final TextInputEditText nameInput = new TextInputEditText(context);
                nameInput.setHint(R.string.name);
                layout.addView(nameInput, 0);

                builder.setView(layout)
                    .setPositiveButton(android.R.string.ok, (dialog, which) -> {
                        final String name = nameInput.getText().toString();

                        shoppingViewModel.addEntry(name);
                    })
                    .setNegativeButton(android.R.string.cancel, (dialog, which) -> dialog.cancel())
                    .show();
            });
    }

}
