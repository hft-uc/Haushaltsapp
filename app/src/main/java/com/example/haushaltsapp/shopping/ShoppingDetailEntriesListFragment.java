package com.example.haushaltsapp.shopping;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.haushaltsapp.R;
import com.example.haushaltsapp.types.ShoppingListDetail;

/**
 * A fragment representing a list of Items.
 */
public class ShoppingDetailEntriesListFragment extends Fragment {

    private ShoppingViewModel shoppingViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        shoppingViewModel = new ViewModelProvider(requireParentFragment()).get(ShoppingViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shopping_detail_entries_list_list, container, false);

        RecyclerView recyclerView = (RecyclerView) view;
        final ShoppingDetailEntriesRecyclerViewAdapter adapter = new ShoppingDetailEntriesRecyclerViewAdapter();
        recyclerView.setAdapter(adapter);

        Transformations.map(shoppingViewModel.getShoppingList(), ShoppingListDetail::getEntries)
            .observe(requireParentFragment().getViewLifecycleOwner(), adapter::updateItems);

        return view;
    }
}