package com.example.haushaltsapp.types;

import androidx.annotation.NonNull;

class User {

    @NonNull
    private String name;

    public User(@NonNull String name) {
        this.name = name;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }
}
