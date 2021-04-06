package com.example.haushaltsapp.ui.gallery;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.haushaltsapp.R;
import com.example.haushaltsapp.types.ShoppingList;
import com.example.haushaltsapp.types.ShoppingListEntry;
import com.example.haushaltsapp.types.User;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


import java.util.ArrayList;
import java.util.List;

public class ShoppingListFragment extends Fragment {
    private String shoppingListId;
    private String m_Text = "";
     Button btn_addShoppingList;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseAuth auth;
    User user;
    private ShoppingListViewModel shoppingListViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        shoppingListViewModel =
                new ViewModelProvider(this).get(ShoppingListViewModel.class);
        View root = inflater.inflate(R.layout.fragment_shoppinglist, container, false);
        final TextView textView = root.findViewById(R.id.text_gallery);

        DocumentReference docRef = db.collection("users").document(FirebaseAuth.getInstance().getCurrentUser().getUid());
        db.collection("users").document(FirebaseAuth.getInstance().getCurrentUser().getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                 user = documentSnapshot.toObject(User.class);

            }
        });

        btn_addShoppingList = root.findViewById(R.id.button_addShoppingList);

        FirebaseUser test =  FirebaseAuth.getInstance().getCurrentUser();
        test.getDisplayName();
        btn_addShoppingList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Neue Liste erstellen");
                // Set up the input
                final EditText input = new EditText(getContext());
                input.setHint("blabla");
                input.setInputType(InputType.TYPE_CLASS_TEXT |  InputType.TYPE_TEXT_FLAG_CAP_WORDS);
                builder.setView(input);


                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //example
                        List<ShoppingListEntry> list = new ArrayList<ShoppingListEntry>();
                        List<User> userlist = new ArrayList<User>();
                        userlist.add(user);

                        m_Text = input.getText().toString();
                        ShoppingList shoplist = new ShoppingList(list,user ,userlist,m_Text);

                        db.collection("listen").document(shoplist.getName()).set(shoplist);
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.show();
            }
        });

        shoppingListViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }

        });
        return root;
    }


}