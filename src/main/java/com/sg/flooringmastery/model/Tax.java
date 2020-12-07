package com.sg.flooringmastery.model;

import java.math.BigDecimal;
import java.util.Objects;

public class Tax {

    private String state;
    private String stateName;
    private BigDecimal taxRate;

    public Tax(String state, String stateName, BigDecimal taxRate) {
        this.state = state;
        this.stateName = stateName;
        this.taxRate = taxRate;
    }

    public String getState() {
        return state;
    }

    public BigDecimal getTaxRate() {
        return taxRate;
    }

    @Override
    public String toString() {
        return "Tax{" +
                "state='" + state + '\'' +
                ", stateName='" + stateName + '\'' +
                ", taxRate=" + taxRate +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tax tax = (Tax) o;
        return Objects.equals(state, tax.state) &&
                Objects.equals(stateName, tax.stateName) &&
                Objects.equals(taxRate, tax.taxRate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(state, stateName, taxRate);
    }
}
