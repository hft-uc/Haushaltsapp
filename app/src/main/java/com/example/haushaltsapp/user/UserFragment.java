package com.example.haushaltsapp.user;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.haushaltsapp.R;

/**
 * A fragment representing a list of users.
 * Only use it in other fragments, not other activities
 */
public class UserFragment extends Fragment {

    private static final String TAG = UserFragment.class.getCanonicalName();

    private UserViewModel userViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        userViewModel = new ViewModelProvider(requireParentFragment()).get(UserViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_list, container, false);

        view.<RecyclerView>findViewById(R.id.user_list).setAdapter(
            new UserRecyclerViewAdapter(userViewModel.createRecyclerOptions(getViewLifecycleOwner()))
        );

        return view;
    }
}