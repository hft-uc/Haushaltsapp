package com.example.haushaltsapp.supply;


import android.util.Log;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.PagedList;

import com.example.haushaltsapp.authentification.AuthRepository;
import com.example.haushaltsapp.shopping.ShoppingRecyclerViewAdapter;
import com.example.haushaltsapp.shopping.ShoppingRepository;
import com.example.haushaltsapp.types.ShoppingListDetail;
import com.example.haushaltsapp.types.ShoppingListEntry;
import com.example.haushaltsapp.types.ShoppingListSummary;
import com.firebase.ui.firestore.paging.FirestorePagingOptions;
import com.google.firebase.firestore.Query;

/**
 * <p>
 * Contains the state for the fragment.
 * </p>
 *
 */
public class SupplyViewModel extends ViewModel {
    private static final String TAG = com.example.haushaltsapp.supply.SupplyViewModel.class.getCanonicalName();

    private final SupplyRepository repository = new SupplyRepository();
    private final AuthRepository authRepository = new AuthRepository();

    private MutableLiveData<ShoppingListDetail> shoppingListDetailLiveData;
    private String id;

    /**
     * Starts to load the {@code ShoppingListDetail} with given id.
     * It can be queried with {@code getShoppingList}
     */
    public void loadShoppingList(String id) {
        this.id = id;

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
                .addOnCompleteListener(task -> result.setValue(task.isSuccessful()));

        return result;
    }

    public LiveData<Boolean> addEntry(String name, String amount) {
        MutableLiveData<Boolean> result = new MutableLiveData<>();

        ShoppingListEntry entry = new ShoppingListEntry(name, amount);
        repository.addShoppingListEntry(id, entry)
                .addOnCompleteListener(task -> result.setValue(task.isSuccessful()));

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
