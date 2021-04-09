package com.example.haushaltsapp.gallery;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.haushaltsapp.types.ShoppingList;

import java.util.List;

public class ShoppingListViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    private final ShoppingListRepository repository = new ShoppingListRepository();

    public ShoppingListViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is gallery fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }

    public LiveData<List<ShoppingList>> getShoppingListOf(String userId) {
        return repository.getShoppingListsOf(userId);
    }


    public LiveData<Boolean> add(ShoppingList shoppingList) {
        return repository.addShoppingList(shoppingList, shoppingList.getOwner().getId());
    }

    public void update(ShoppingList shoppingList) {

    }

    public void delete(ShoppingList shoppingList) {
    }

}