package com.example.haushaltsapp.shopping;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.haushaltsapp.R;
import com.example.haushaltsapp.types.ShoppingListDetail;

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
        final ShoppingDetailEntriesRecyclerViewAdapter adapter = new ShoppingDetailEntriesRecyclerViewAdapter();
        recyclerView.setAdapter(adapter);

        view.findViewById(R.id.add_shopping_list_entry_fab)
            .setOnClickListener(v -> {
                final AddEntryDialog dialog = new AddEntryDialog();
                dialog.setCancelable(false);
                dialog.showNow(getChildFragmentManager(), "tag");
            });

        Transformations.map(shoppingViewModel.getShoppingList(), ShoppingListDetail::getEntries)
            .observe(requireParentFragment().getViewLifecycleOwner(), adapter::updateItems);

        return view;
    }

    public static class AddEntryDialog extends DialogFragment {

        private EditText name;
        private EditText amount;

        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.dialog_add_shopping_detail_entry, container, false);

            name = view.findViewById(R.id.add_shopping_entry_name_edit_text);
            amount = view.findViewById(R.id.add_shopping_entry_amount_edit_text);

            return view;
        }


//        @NonNull
//        @Override
//        public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
//
//            View view = inflater.inflate(R.layout.dialog_add_shopping_detail_entry, container);
//
//            name = view.findViewById(R.id.add_shopping_entry_name_edit_text);
//            amount = view.findViewById(R.id.add_shopping_entry_amount_edit_text);
//
//            return view;
//        }

        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setStyle(DialogFragment.STYLE_NORMAL, R.style.Theme_Haushaltsapp);
        }
    }
}
