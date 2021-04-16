package com.example.haushaltsapp.shopping;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.haushaltsapp.R;
import com.example.haushaltsapp.user.UserViewModel;

public class ShoppingDetailFragment extends Fragment {
    private static final String TAG = ShoppingDetailFragment.class.getCanonicalName();

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

        shoppingViewModel.getShoppingList().observe(getViewLifecycleOwner(), shoppingListDetail -> {
            root.<TextView>findViewById(R.id.shopping_detail_name).setText(shoppingListDetail.getName());

            userViewModel.updateUsers(shoppingListDetail.getMembers());
        });

        return root;
    }
}
