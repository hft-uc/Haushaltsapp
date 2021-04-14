package com.example.haushaltsapp.shopping;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.PagedList;

import com.example.haushaltsapp.authentification.UserRepository;
import com.example.haushaltsapp.types.ShoppingListDetail;
import com.example.haushaltsapp.types.ShoppingListSummary;
import com.firebase.ui.firestore.paging.FirestorePagingOptions;
import com.google.firebase.firestore.Query;

import java.util.List;

public class ShoppingViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    private final ShoppingRepository repository = new ShoppingRepository();
    private final UserRepository userRepository = new UserRepository();
    private final LifecycleOwner lifecycleOwner;

    public ShoppingViewModel(LifecycleOwner lifecycleOwner) {
        this.lifecycleOwner = lifecycleOwner;
        mText = new MutableLiveData<>();
        mText.setValue("This is gallery fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }

    public LiveData<List<ShoppingListSummary>> getShoppingList() {
        MutableLiveData<List<ShoppingListSummary>> result = new MutableLiveData<>();

        repository.getShoppingListsOf(userRepository.getCurrentUser().getId())
            .observe(lifecycleOwner, result::setValue);

        return result;
    }

    public LiveData<Boolean> add(String name) {
        ShoppingListDetail shoppingList = new ShoppingListDetail(name, userRepository.getCurrentUser());

        return repository.addShoppingList(shoppingList, shoppingList.getOwner().getId());
    }

    public void update(ShoppingListDetail shoppingListDetail) {
        throw new UnsupportedOperationException();
    }

    public void delete(ShoppingListDetail shoppingListDetail) {
        throw new UnsupportedOperationException();
    }

    public ShoppingRecyclerViewAdapter createAdapter() {
        PagedList.Config config = new PagedList.Config.Builder()
            .setEnablePlaceholders(true)
            .setPrefetchDistance(10)
            .setPageSize(20)
            .build();

        final Query query = repository.getShoppingListsQuery(userRepository.getCurrentUser().getId());

        FirestorePagingOptions<ShoppingListSummary> options
            = new FirestorePagingOptions.Builder<ShoppingListSummary>()
            .setLifecycleOwner(lifecycleOwner)
            .setQuery(query, config, ShoppingListSummary.class)
            .build();

        return new ShoppingRecyclerViewAdapter(options);
    }

}