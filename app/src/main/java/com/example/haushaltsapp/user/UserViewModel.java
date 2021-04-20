package com.example.haushaltsapp.user;

import android.util.Log;

import androidx.lifecycle.ViewModel;

import com.example.haushaltsapp.types.UserSummary;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.Query;

/**
 * Used to communicate between the parent fragment and {@code UserFragment} to pass in the users
 * that need to be displayed
 */
public class UserViewModel extends ViewModel {
    private static final String TAG = UserViewModel.class.getCanonicalName();

    private final UserRepository repository = new UserRepository();
    private UserSource source;
    private String id;

    public FirestoreRecyclerOptions<UserSummary> createRecyclerOptions() {
        Query query = null;
        switch (source) {
            case SHOPPING:
                query = repository.getShoppingListMembers(id);
                break;
            case SUPPLY:
            case FINANCE:
            case CHAT:
                query = repository.getMembers(id);
                break;
        }

        Log.i(TAG, "Created FirestoreRecyclerOptions");
        return new FirestoreRecyclerOptions.Builder<UserSummary>()
            .setQuery(query, UserSummary.class)
            .build();
    }

    public void setSource(UserSource source) {
        Log.i(TAG, "Set source to " + source.name());
        this.source = source;
    }

    public void setId(String id) {
        Log.i(TAG, "Set id to " + id);
        this.id = id;
    }
}
