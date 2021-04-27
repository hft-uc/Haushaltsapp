package com.example.haushaltsapp.chat;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ChatViewModel extends ViewModel {
    private final MutableLiveData<String> mText;

    public ChatViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is a Slide for Chat");
    }

    public MutableLiveData<String> getText() {
        return mText;
    }
}
