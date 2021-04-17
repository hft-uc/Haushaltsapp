package com.example.haushaltsapp.shopping;

import android.util.Log;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.PagedList;

import com.example.haushaltsapp.authentification.AuthRepository;
import com.example.haushaltsapp.types.ShoppingListDetail;
import com.example.haushaltsapp.types.ShoppingListSummary;
import com.firebase.ui.firestore.paging.FirestorePagingOptions;
import com.google.firebase.firestore.Query;

public class ShoppingViewModel extends ViewModel {
    private static final String TAG = ShoppingViewModel.class.getCanonicalName();

    private final ShoppingRepository repository = new ShoppingRepository();
    private final AuthRepository authRepository = new AuthRepository();

    private MutableLiveData<ShoppingListDetail> shoppingListDetailLiveData;

    /**
     * Starts to load the {@code ShoppingListDetail} with given id.
     * It can be queried with {@code getShoppingList}
     */
    public void loadShoppingList(String id) {
        shoppingListDetailLiveData = new MutableLiveData<>();

        repository.getShoppingList(id)
            .addSnapshotListener((value, error) -> {
                    if (value != null) {
                        shoppingListDetailLiveData.setValue(value.toObject(ShoppingListDetail.class));
                    } else {
                        Log.w(TAG, "loadShoppingList failed", error);
                    }
                }
            );
    }

    public LiveData<ShoppingListDetail> getShoppingList() {
        return shoppingListDetailLiveData;
    }


    public LiveData<Boolean> add(String name) {
        MutableLiveData<Boolean> result = new MutableLiveData<>();

        ShoppingListDetail shoppingList = new ShoppingListDetail(name, authRepository.getCurrentUser());
        repository.addShoppingList(shoppingList, shoppingList.getOwner().getId())
            .addOnSuccessListener(value -> result.setValue(true))
            .addOnFailureListener(exception -> result.setValue(false));

        return result;
    }

    public void update(ShoppingListDetail shoppingListDetail) {
        throw new UnsupportedOperationException();
    }

    public void delete(ShoppingListDetail shoppingListDetail) {
        throw new UnsupportedOperationException();
    }

    public ShoppingRecyclerViewAdapter createShoppingListAdapter(LifecycleOwner lifecycleOwner) {
        PagedList.Config config = new PagedList.Config.Builder()
            .setEnablePlaceholders(true)
            .setPrefetchDistance(10)
            .setPageSize(20)
            .build();

        final Query query = repository.getShoppingLists(authRepository.getCurrentUser().getId());

        FirestorePagingOptions<ShoppingListSummary> options
            = new FirestorePagingOptions.Builder<ShoppingListSummary>()
            .setLifecycleOwner(lifecycleOwner)
            .setQuery(query, config, ShoppingListSummary.class)
            .build();

        return new ShoppingRecyclerViewAdapter(options);
    }
}
