package com.example.haushaltsapp.user;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.haushaltsapp.types.UserSummary;

import java.util.List;

/**
 * Used to communicate between the parent fragment and {@code UserFragment} to pass in the users
 * that need to be displayed
 */
public class UserViewModel extends ViewModel {

    private final MutableLiveData<List<UserSummary>> users = new MutableLiveData<>();

    public LiveData<List<UserSummary>> getUsers() {
        return users;
    }

    public void updateUsers(List<UserSummary> users) {
        this.users.postValue(users);
    }
}
