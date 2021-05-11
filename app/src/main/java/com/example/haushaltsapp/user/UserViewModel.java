package com.example.haushaltsapp.user;

import android.util.Log;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.haushaltsapp.types.Budget;
import com.example.haushaltsapp.types.ShoppingListDetail;
import com.example.haushaltsapp.types.UserSummary;
import com.example.haushaltsapp.utils.FirestoreExtensionsKt;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.Query;

import java.util.List;

/**
 * Used to communicate between the parent fragment and {@code UserFragment} to pass in the users
 * that need to be displayed
 */
public class UserViewModel extends ViewModel {
    private static final String TAG = UserViewModel.class.getCanonicalName();

    private final UserRepository repository = new UserRepository();
    private UserSource source;
    private String id;
    private ShoppingListDetail shoppingListDetail;
    private Budget budget;

    public FirestoreRecyclerOptions<UserSummary> createRecyclerOptions(LifecycleOwner lifecycleOwner) {
        Query query = getQuery();

        Log.i(TAG, "Created FirestoreRecyclerOptions");
        return new FirestoreRecyclerOptions.Builder<UserSummary>()
            .setQuery(query, UserSummary.class)
            .setLifecycleOwner(lifecycleOwner)
            .build();
    }

    public LiveData<List<UserSummary>> getMembers() {
        MutableLiveData<List<UserSummary>> result = new MutableLiveData<>();

        getQuery().addSnapshotListener((value, error) -> {
            if (error == null) {
                List<UserSummary> users = FirestoreExtensionsKt.toObjectList(value.getDocuments(), UserSummary.class);
                result.setValue(users);
            } else {
                Log.w(TAG, "Failed to load members");
            }
        });

        return result;
    }

    /**
     * Friend system currently not implemented, just returns all users
     */
    public LiveData<List<UserSummary>> getFriends() {
        MutableLiveData<List<UserSummary>> result = new MutableLiveData<>();

        repository.getAllUsers().addSnapshotListener((value, error) -> {
            if (error == null) {
                List<UserSummary> users = FirestoreExtensionsKt.toObjectList(value.getDocuments(), UserSummary.class);
                result.setValue(users);
            } else {
                Log.w(TAG, "Failed to load all friends", error);
            }
        });

        return result;
    }

    private Query getQuery() {
        Query query = null;
        switch (source) {
            case SHOPPING:
                query = repository.getShoppingListMembers(id);
                break;
            case SUPPLY:
            case FINANCE:
                query = repository.getShoppingListMembers(id);
                break;
            case CHAT:
                query = repository.getMembers(id);
                break;
        }
        return query;
    }

    public UserSummary getOwner() {
        switch (source) {
            case SHOPPING:
                return shoppingListDetail.getOwner();
            case SUPPLY:
            case FINANCE:
                return budget.getOwner();
            case CHAT:
            default:
                return null;
        }
    }

    public void addMember(UserSummary user) {
        switch (source) {
            case SHOPPING:
                repository.addShoppingListMember(shoppingListDetail, user);
                break;
            case SUPPLY:
            case FINANCE:
            case CHAT:
                break;
        }
    }

    public void removeMember(UserSummary user) {
        switch (source) {
            case SHOPPING:
                repository.removeShoppingListMember(shoppingListDetail, user);
                break;
            case SUPPLY:
            case FINANCE:
            case CHAT:
                break;
        }
    }

    public void setSource(UserSource source) {
        Log.i(TAG, "Set source to " + source.name());
        this.source = source;
    }

    public void setId(String id) {
        Log.i(TAG, "Set id to " + id);
        this.id = id;
    }

    public void setShoppingListDetail(ShoppingListDetail detail) {
        Log.i(TAG, "Set shopping list detail  to " + detail);
        this.shoppingListDetail = detail;
    }

    public void setBudget(Budget detail) {
        Log.i(TAG, "Set Budget to " + detail);
        this.budget = detail;
    }


}
