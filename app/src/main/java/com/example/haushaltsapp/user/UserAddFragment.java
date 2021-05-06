package com.example.haushaltsapp.user;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.haushaltsapp.R;

public class UserAddFragment extends Fragment {

    private UserViewModel userViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        userViewModel = new ViewModelProvider(requireParentFragment()).get(UserViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_user_add_list, container, false);

        UserAddRecyclerViewAdapter adapter = new UserAddRecyclerViewAdapter(
            user -> userViewModel.addMember(user)
        );
        RecyclerView recyclerView = root.findViewById(R.id.user_add_list);
        recyclerView.setAdapter(adapter);

        adapter.setOwner(userViewModel.getOwner());
        userViewModel.getFriends().observe(getViewLifecycleOwner(), adapter::updateUsers);
        userViewModel.getMembers().observe(getViewLifecycleOwner(), adapter::updateMembers);

        EditText query = root.findViewById(R.id.user_add_search_edit_text);
        query.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //nothing
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //nothing
            }

            @Override
            public void afterTextChanged(Editable s) {
                adapter.updateSearchQuery(s.toString());
            }
        });

        return root;
    }
}
