package com.example.haushaltsapp.finance;

import java.util.List;

public class MonthlySurplus {

    private List<MonthlyIncome> monthlyReceipts;

    private List<TotalExpenditures> totalExpenditures;

    public MonthlySurplus(List<MonthlyIncome> monthlyReceipts,
                          List<TotalExpenditures> totalExpenditures) {
        this.monthlyReceipts = monthlyReceipts;
        this.totalExpenditures = totalExpenditures;
    }

    public List<MonthlyIncome> getMonthlyReceipts() {
        return monthlyReceipts;
    }

    public void setMonthlyReceipts(List<MonthlyIncome> monthlyReceipts) {
        this.monthlyReceipts = monthlyReceipts;
    }

    public List<TotalExpenditures> getTotalExpenditures() {
        return totalExpenditures;
    }

    public void setTotalExpenditures(List<TotalExpenditures> totalExpenditures) {
        this.totalExpenditures = totalExpenditures;
    }
}
