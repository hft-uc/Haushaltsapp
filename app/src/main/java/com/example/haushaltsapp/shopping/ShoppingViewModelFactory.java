package com.example.haushaltsapp.shopping;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

class ShoppingViewModelFactory implements ViewModelProvider.Factory {

    private final LifecycleOwner lifecycleOwner;

    public ShoppingViewModelFactory(LifecycleOwner lifecycleOwner) {
        this.lifecycleOwner = lifecycleOwner;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        //noinspection unchecked
        return (T) new ShoppingViewModel(lifecycleOwner);
    }
}
