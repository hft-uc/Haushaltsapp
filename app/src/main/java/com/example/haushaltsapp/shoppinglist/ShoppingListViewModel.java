package com.example.haushaltsapp.shoppinglist;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.haushaltsapp.authentification.UserRepository;
import com.example.haushaltsapp.types.ShoppingListDetail;
import com.example.haushaltsapp.types.ShoppingListSummary;

import java.util.List;

public class ShoppingListViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    private final ShoppingListRepository repository = new ShoppingListRepository();
    private final UserRepository userRepository = new UserRepository();
    private final LifecycleOwner lifecycleOwner;

    public ShoppingListViewModel(LifecycleOwner lifecycleOwner) {
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

}