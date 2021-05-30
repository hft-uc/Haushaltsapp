package com.example.haushaltsapp.supply;

public class SupplyItemModel {
    String name;
    String date;
    int amount;

    SupplyItemModel(){

    }

    public SupplyItemModel(String name, String date, int amount) {
        this.name = name;
        this.date = date;
        this.amount = amount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }


}
