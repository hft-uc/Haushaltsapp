package com.example.haushaltsapp.finance;

public class Investment {

    private double credit;
    private double savings;
    private double other;

    public Investment(double credit, double savings, double other) {
        this.credit = credit;
        this.savings = savings;
        this.other = other;
    }

    public double getCredit() {
        return credit;
    }

    public void setCredit(double credit) {
        this.credit = credit;
    }

    public double getSavings() {
        return savings;
    }

    public void setSavings(double savings) {
        this.savings = savings;
    }

    public double getOther() {
        return other;
    }

    public void setOther(double other) {
        this.other = other;
    }
}
