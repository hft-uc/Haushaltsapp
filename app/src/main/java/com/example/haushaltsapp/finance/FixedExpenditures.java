package com.example.haushaltsapp.finance;

public class FixedExpenditures {

    private double rent;
    private double insurances;
    private double mobility;
    private double leisure;

    public FixedExpenditures(double rent, double insurances, double mobility, double leisure) {
        this.rent = rent;
        this.insurances = insurances;
        this.mobility = mobility;
        this.leisure = leisure;
    }

    public double getRent() {
        return rent;
    }

    public void setRent(double rent) {
        this.rent = rent;
    }

    public double getInsurances() {
        return insurances;
    }

    public void setInsurances(double insurances) {
        this.insurances = insurances;
    }

    public double getMobility() {
        return mobility;
    }

    public void setMobility(double mobility) {
        this.mobility = mobility;
    }

    public double getLeisure() {
        return leisure;
    }

    public void setLeisure(double leisure) {
        this.leisure = leisure;
    }
}
