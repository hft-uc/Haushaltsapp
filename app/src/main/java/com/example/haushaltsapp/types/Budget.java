package com.example.haushaltsapp.types;

import androidx.annotation.NonNull;

import java.util.List;

class Budget {

    @NonNull
    private List<Expenditure> expenditures;

    @NonNull
    private List<Income> incomes;

    @NonNull
    private User owner;

    @NonNull
    private List<User> members;

    public Budget(@NonNull List<Expenditure> expenditures, @NonNull List<Income> incomes, @NonNull User owner, @NonNull List<User> members) {
        this.expenditures = expenditures;
        this.incomes = incomes;
        this.owner = owner;
        this.members = members;
    }

    @NonNull
    public List<Expenditure> getExpenditures() {
        return expenditures;
    }

    public void setExpenditures(@NonNull List<Expenditure> expenditures) {
        this.expenditures = expenditures;
    }

    @NonNull
    public List<Income> getIncomes() {
        return incomes;
    }

    public void setIncomes(@NonNull List<Income> incomes) {
        this.incomes = incomes;
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
