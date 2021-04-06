package com.example.haushaltsapp.finance;

import java.util.List;

public class TotalExpenditures {

    private List<FixedExpenditures> fixedExpenditures;

    private List<VariableExpenditures> variableExpenditures;

    private List<Investment> investments;

    public TotalExpenditures(List<FixedExpenditures> fixedExpenditures,
                             List<VariableExpenditures> variableExpenditures,
                             List<Investment> investments) {
        this.fixedExpenditures = fixedExpenditures;
        this.variableExpenditures = variableExpenditures;
        this.investments = investments;
    }

    public List<FixedExpenditures> getFixedExpenditures() {
        return fixedExpenditures;
    }

    public void setFixedExpenditures(List<FixedExpenditures> fixedExpenditures) {
        this.fixedExpenditures = fixedExpenditures;
    }

    public List<VariableExpenditures> getVariableExpenditures() {
        return variableExpenditures;
    }

    public void setVariableExpenditures(List<VariableExpenditures> variableExpenditures) {
        this.variableExpenditures = variableExpenditures;
    }

    public List<Investment> getInvestments() {
        return investments;
    }

    public void setInvestments(List<Investment> investments) {
        this.investments = investments;
    }
}
