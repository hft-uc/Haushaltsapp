package com.example.haushaltsapp.shoppinglist;

import android.app.AlertDialog;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.haushaltsapp.R;
import com.example.haushaltsapp.types.ShoppingListDetail;
import com.example.haushaltsapp.types.UserDetail;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class ShoppingListFragment extends Fragment {
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private UserDetail userDetail;
    private ShoppingListViewModel shoppingListViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        shoppingListViewModel =
                new ViewModelProvider(this).get(ShoppingListViewModel.class);
        View root = inflater.inflate(R.layout.fragment_shoppinglist, container, false);
        final TextView textView = root.findViewById(R.id.text_gallery);

        db.collection("users")
            .document(FirebaseAuth.getInstance().getCurrentUser().getUid())
            .get()
            .addOnSuccessListener(documentSnapshot -> {
                    userDetail = documentSnapshot.toObject(UserDetail.class);
                    shoppingListViewModel.getShoppingListOf(userDetail.getId()).observe(getViewLifecycleOwner(), shoppingLists -> Log.d("shopping_list", shoppingLists.toString()));

                }

            );

        Button btnAddShoppingList = root.findViewById(R.id.button_addShoppingList);

        FirebaseUser test = FirebaseAuth.getInstance().getCurrentUser();
        test.getDisplayName();
        btnAddShoppingList.setOnClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setTitle("Neue Liste erstellen");
            // Set up the input
            final EditText input = new EditText(getContext());
            input.setHint("blabla");
            input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_WORDS);
            builder.setView(input);

            builder.setPositiveButton("OK", (dialog, which) -> {
                String text = input.getText().toString();
                ShoppingListDetail shoplist = new ShoppingListDetail(text, userDetail.toSummary());

                shoppingListViewModel.add(shoplist);
            });
            builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());

            builder.show();
        });

        shoppingListViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        return root;
    }


}