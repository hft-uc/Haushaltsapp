package com.example.haushaltsapp.shoppinglist;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

class ShoppingListViewModelFactory implements ViewModelProvider.Factory {

    private final LifecycleOwner lifecycleOwner;

    public ShoppingListViewModelFactory(LifecycleOwner lifecycleOwner) {
        this.lifecycleOwner = lifecycleOwner;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        //noinspection unchecked
        return (T) new ShoppingListViewModel(lifecycleOwner);
    }
}
