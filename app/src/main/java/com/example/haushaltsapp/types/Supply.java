package com.example.haushaltsapp.types;

import androidx.annotation.NonNull;

import java.util.List;

class Supply {

    @NonNull
    private List<SupplyEntry> entries;

    @NonNull
    private User owner;

    @NonNull
    private List<User> members;

    public Supply(@NonNull List<SupplyEntry> entries, @NonNull User owner, @NonNull List<User> members) {
        this.entries = entries;
        this.owner = owner;
        this.members = members;
    }

    @NonNull
    public List<SupplyEntry> getEntries() {
        return entries;
    }

    public void setEntries(@NonNull List<SupplyEntry> entries) {
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
