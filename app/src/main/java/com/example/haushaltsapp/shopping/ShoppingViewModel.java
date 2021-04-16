package com.example.haushaltsapp.shopping;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.PagedList;

import com.example.haushaltsapp.authentification.AuthRepository;
import com.example.haushaltsapp.types.ShoppingListDetail;
import com.example.haushaltsapp.types.ShoppingListSummary;
import com.firebase.ui.firestore.paging.FirestorePagingOptions;
import com.google.firebase.firestore.Query;

public class ShoppingViewModel extends ViewModel {
    private final ShoppingRepository repository = new ShoppingRepository();
    private final AuthRepository authRepository = new AuthRepository();

    private LiveData<ShoppingListDetail> shoppingListDetailLiveData;

    /**
     * Starts to load the {@code ShoppingListDetail} with given id.
     * It can be queried with {@code getShoppingList}
     */
    public void loadShoppingList(String id) {
        shoppingListDetailLiveData = repository.getShoppingList(id);
    }

    public LiveData<ShoppingListDetail> getShoppingList() {
        return shoppingListDetailLiveData;
    }

    public LiveData<Boolean> add(String name) {
        ShoppingListDetail shoppingList = new ShoppingListDetail(name, authRepository.getCurrentUser());

        return repository.addShoppingList(shoppingList, shoppingList.getOwner().getId());
    }

    public void update(ShoppingListDetail shoppingListDetail) {
        throw new UnsupportedOperationException();
    }

    public void delete(ShoppingListDetail shoppingListDetail) {
        throw new UnsupportedOperationException();
    }

    public ShoppingRecyclerViewAdapter createAdapter(LifecycleOwner lifecycleOwner) {
        PagedList.Config config = new PagedList.Config.Builder()
            .setEnablePlaceholders(true)
            .setPrefetchDistance(10)
            .setPageSize(20)
            .build();

        final Query query = repository.getShoppingListsQuery(authRepository.getCurrentUser().getId());

        FirestorePagingOptions<ShoppingListSummary> options
            = new FirestorePagingOptions.Builder<ShoppingListSummary>()
            .setLifecycleOwner(lifecycleOwner)
            .setQuery(query, config, ShoppingListSummary.class)
            .build();

        return new ShoppingRecyclerViewAdapter(options);
    }

}