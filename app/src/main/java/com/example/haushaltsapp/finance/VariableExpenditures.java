package com.example.haushaltsapp.finance;

public class VariableExpenditures {

    private double living;
    private double entertainment;
    private double gifts;

    public VariableExpenditures(double living, double entertainment, double gifts) {
        this.living = living;
        this.entertainment = entertainment;
        this.gifts = gifts;
    }

    public double getLiving() {
        return living;
    }

    public void setLiving(double living) {
        this.living = living;
    }

    public double getEntertainment() {
        return entertainment;
    }

    public void setEntertainment(double entertainment) {
        this.entertainment = entertainment;
    }

    public double getGifts() {
        return gifts;
    }

    public void setGifts(double gifts) {
        this.gifts = gifts;
    }
}
