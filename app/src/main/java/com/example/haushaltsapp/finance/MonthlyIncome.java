package com.example.haushaltsapp.finance;

public class MonthlyIncome {

    private double netSalary;
    private double secondJob;
    private double others;

    public MonthlyIncome(double netSalary, double secondJob, double others) {
        this.netSalary = netSalary;
        this.secondJob = secondJob;
        this.others = others;
    }

    public double getNetSalary() {
        return netSalary;
    }

    public void setNetSalary(double netSalary) {
        this.netSalary = netSalary;
    }

    public double getSecondJob() {
        return secondJob;
    }

    public void setSecondJob(double secondJob) {
        this.secondJob = secondJob;
    }

    public double getOthers() {
        return others;
    }

    public void setOthers(double others) {
        this.others = others;
    }
}
