package com.example.haushaltsapp.types;

import androidx.annotation.NonNull;

public class User {

    @NonNull
    private String name ="";
    private String email="";
    private String id="";

    public User(@NonNull String name, String email, String id) {
        this.name = name;
        this.email= email;
        this.id=id;
    }
public User() {

}

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }
}
