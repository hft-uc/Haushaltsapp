package com.example.haushaltsapp.chat;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ChatDetailViewModel extends ViewModel {
    private MutableLiveData<String> mText;

    public ChatDetailViewModel() {
        mText = new MutableLiveData<>();
    }

    public LiveData<String> getText() {
        return mText;
    }
}
