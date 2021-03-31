package com.example.haushaltsapp.types;

import androidx.annotation.NonNull;

class ShoppingListEntry {

    @NonNull
    private String name;

    @NonNull
    private String amount;

    private boolean done;

    public ShoppingListEntry(@NonNull String name, @NonNull String amount, boolean done) {
        this.name = name;
        this.amount = amount;
        this.done = done;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    @NonNull
    public String getAmount() {
        return amount;
    }

    public void setAmount(@NonNull String amount) {
        this.amount = amount;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }
}
