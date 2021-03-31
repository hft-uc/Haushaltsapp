package com.example.haushaltsapp.types;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.time.ZonedDateTime;

class SupplyEntry {

    @NonNull
    private String name;

    @NonNull
    private String amount;

    @Nullable
    private ZonedDateTime expiry;

    public SupplyEntry(@NonNull String name, @NonNull String amount, @Nullable ZonedDateTime expiry) {
        this.name = name;
        this.amount = amount;
        this.expiry = expiry;
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

    @Nullable
    public ZonedDateTime getExpiry() {
        return expiry;
    }

    public void setExpiry(@Nullable ZonedDateTime expiry) {
        this.expiry = expiry;
    }
}
