package com.example.haushaltsapp.authentification;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

public class AuthViewModel extends ViewModel {

    private final AuthRepository repository = new AuthRepository();

    public LiveData<Boolean> signIn(String email, String password) {
        return repository.signIn(email, password);
    }

    public boolean alreadySignedIn() {
        return repository.alreadySignedIn();
    }

    public LiveData<String> register(String email, String password, String name) {
        return repository.register(email, password, name);
    }

    public void logout() {
        repository.logout();
    }

    public void updateStatus(String status) {
        repository.updateStatus(status);
    }

}
