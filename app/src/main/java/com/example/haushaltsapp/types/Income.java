package com.example.haushaltsapp.types;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.time.ZonedDateTime;

class Income {

    private double amount;

    @NonNull
    private String name;

    @Nullable
    private ZonedDateTime date;

    public Income(double amount, @NonNull String name, @Nullable ZonedDateTime date) {
        this.amount = amount;
        this.name = name;
        this.date = date;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    @Nullable
    public ZonedDateTime getDate() {
        return date;
    }

    public void setDate(@Nullable ZonedDateTime date) {
        this.date = date;
    }
}
