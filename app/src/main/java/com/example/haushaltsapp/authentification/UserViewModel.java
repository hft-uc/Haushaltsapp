package com.example.haushaltsapp.authentification;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

public class UserViewModel extends ViewModel {

    private final UserRepository repository = new UserRepository();

    public LiveData<Boolean> signIn(String email, String password) {
        return repository.signIn(email, password);
    }

    public boolean alreadySignedIn() {
        return repository.alreadySignedIn();
    }

    public LiveData<Boolean> register(String email, String password, String name) {
        return repository.register(email, password, name);
    }
}
