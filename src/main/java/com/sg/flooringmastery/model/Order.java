package com.sg.flooringmastery.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.Objects;

public class Order {
    private int orderNumber;
    private LocalDate orderDate;
    private String customerName;
    private String state;
    private BigDecimal taxRate;
    private String productType;
    private BigDecimal area;
    private BigDecimal costPerSquareFoot;
    private BigDecimal labourCostPerSquareFoot;
    private BigDecimal materialCost;
    private BigDecimal labourCost;
    private BigDecimal tax;
    private BigDecimal total;

    public Order(int orderNumber, LocalDate orderDate, String customerName, String state, BigDecimal taxRate, String productType, BigDecimal area, BigDecimal costPerSquareFoot, BigDecimal labourCostPerSquareFoot, BigDecimal materialCost, BigDecimal labourCost, BigDecimal tax, BigDecimal total) {
        this.orderNumber = orderNumber;
        this.orderDate = orderDate;
        this.customerName = customerName;
        this.state = state;
        this.taxRate = taxRate;
        this.productType = productType;
        this.area = area;
        this.costPerSquareFoot = costPerSquareFoot;
        this.labourCostPerSquareFoot = labourCostPerSquareFoot;
        this.materialCost = materialCost;
        this.labourCost = labourCost;
        this.tax = tax;
        this.total = total;
    }

    public Order(LocalDate date, String customerName, String state, String productType, BigDecimal area, BigDecimal taxRate, BigDecimal costPerSqFt, BigDecimal labourCostPerSqFt) {
        this.orderDate = date;
        this.customerName = customerName;
        this.state = state;
        this.productType = productType;
        this.area = area;
        this.taxRate = taxRate;
        this.costPerSquareFoot = costPerSqFt;
        this.labourCostPerSquareFoot = labourCostPerSqFt;

        BigDecimal materialCost = area.multiply(costPerSqFt);
        BigDecimal labourCost = area.multiply(labourCostPerSqFt);
        BigDecimal tax = materialCost.add(labourCost);
        tax = tax.multiply(taxRate.divide(new BigDecimal("100"), 2, RoundingMode.HALF_UP));
        BigDecimal total = materialCost.add(labourCost).add(tax);

        this.materialCost = materialCost.setScale(2, RoundingMode.HALF_UP);
        this.labourCost = labourCost.setScale(2, RoundingMode.HALF_UP);
        this.tax = tax.setScale(2, RoundingMode.HALF_UP);
        this.total = total.setScale(2, RoundingMode.HALF_UP);

    }

    public int getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(int orderNumber) {
        this.orderNumber = orderNumber;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public BigDecimal getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(BigDecimal taxRate) {
        this.taxRate = taxRate;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public BigDecimal getArea() {
        return area;
    }

    public void setArea(BigDecimal area) {
        this.area = area;
    }

    public BigDecimal getCostPerSquareFoot() {
        return costPerSquareFoot;
    }

    public void setCostPerSquareFoot(BigDecimal costPerSquareFoot) {
        this.costPerSquareFoot = costPerSquareFoot;
    }

    public BigDecimal getLabourCostPerSquareFoot() {
        return labourCostPerSquareFoot;
    }

    public BigDecimal getMaterialCost() {
        return materialCost;
    }

    public BigDecimal getLabourCost() {
        return labourCost;
    }

    public BigDecimal getTax() {
        return tax;
    }

    public void setTax(BigDecimal tax) {
        this.tax = tax;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderNumber=" + orderNumber +
                ", orderDate=" + orderDate +
                ", customerName='" + customerName + '\'' +
                ", state='" + state + '\'' +
                ", taxRate=" + taxRate +
                ", productType='" + productType + '\'' +
                ", area=" + area +
                ", costPerSquareFoot=" + costPerSquareFoot +
                ", labourCostPerSquareFoot=" + labourCostPerSquareFoot +
                ", materialCost=" + materialCost +
                ", labourCost=" + labourCost +
                ", tax=" + tax +
                ", total=" + total +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return orderNumber == order.orderNumber &&
                Objects.equals(orderDate, order.orderDate) &&
                Objects.equals(customerName, order.customerName) &&
                Objects.equals(state, order.state) &&
                Objects.equals(taxRate, order.taxRate) &&
                Objects.equals(productType, order.productType) &&
                Objects.equals(area, order.area) &&
                Objects.equals(costPerSquareFoot, order.costPerSquareFoot) &&
                Objects.equals(labourCostPerSquareFoot, order.labourCostPerSquareFoot) &&
                Objects.equals(materialCost, order.materialCost) &&
                Objects.equals(labourCost, order.labourCost) &&
                Objects.equals(tax, order.tax) &&
                Objects.equals(total, order.total);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderNumber, orderDate, customerName, state, taxRate, productType, area, costPerSquareFoot, labourCostPerSquareFoot, materialCost, labourCost, tax, total);
    }
}
