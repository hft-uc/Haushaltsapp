package com.example.haushaltsapp.types;

import androidx.annotation.NonNull;

import java.util.List;

public class ShoppingList {

    public String getName() {
        return name;
    }

    private final String name;
    @NonNull
    private List<ShoppingListEntry> entries;

    @NonNull
    private User owner;

    @NonNull
    private List<User> members;

    public ShoppingList(@NonNull List<ShoppingListEntry> entries, @NonNull User owner, @NonNull List<User> members, String name) {
        this.entries = entries;
        this.owner = owner;
        this.members = members;
        this.name = name;
    }

    @NonNull
    public List<ShoppingListEntry> getEntries() {
        return entries;
    }

    public void setEntries(@NonNull List<ShoppingListEntry> entries) {
        this.entries = entries;
    }

    @NonNull
    public User getOwner() {
        return owner;
    }

    public void setOwner(@NonNull User owner) {
        this.owner = owner;
    }

    @NonNull
    public List<User> getMembers() {
        return members;
    }

    public void setMembers(@NonNull List<User> members) {
        this.members = members;
    }
}
