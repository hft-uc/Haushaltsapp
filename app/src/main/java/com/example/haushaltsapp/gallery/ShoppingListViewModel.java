package com.example.haushaltsapp.gallery;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.haushaltsapp.types.ShoppingListDetail;
import com.example.haushaltsapp.types.ShoppingListSummary;

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

    public LiveData<List<ShoppingListSummary>> getShoppingListOf(String userId) {
        return repository.getShoppingListsOf(userId);
    }


    public LiveData<Boolean> add(ShoppingListDetail shoppingListDetail) {
        return repository.addShoppingList(shoppingListDetail, shoppingListDetail.getOwner().getId());
    }

    public void update(ShoppingListDetail shoppingListDetail) {
        throw new UnsupportedOperationException();
    }

    public void delete(ShoppingListDetail shoppingListDetail) {
        throw new UnsupportedOperationException();
    }

}