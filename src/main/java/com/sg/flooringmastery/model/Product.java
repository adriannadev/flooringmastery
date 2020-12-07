package com.sg.flooringmastery.model;

import java.math.BigDecimal;
import java.util.Objects;

public class Product {

    private String productType;
    private BigDecimal costPerSquareFoot;
    private BigDecimal labourCostPerSquareFoot;

    public Product(String productType, BigDecimal costPerSquareFoot, BigDecimal labourCostPerSquareFoot) {
        this.productType = productType;
        this.costPerSquareFoot = costPerSquareFoot;
        this.labourCostPerSquareFoot = labourCostPerSquareFoot;
    }

    public String getProductType() {
        return productType;
    }

    public BigDecimal getCostPerSquareFoot() {
        return costPerSquareFoot;
    }

    public BigDecimal getLabourCostPerSquareFoot() {
        return labourCostPerSquareFoot;
    }

    @Override
    public String toString() {
        return "Product{" +
                "productType='" + productType + '\'' +
                ", costPerSquareFoot=" + costPerSquareFoot +
                ", labourCostPerSquareFoot=" + labourCostPerSquareFoot +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(productType, product.productType) &&
                Objects.equals(costPerSquareFoot, product.costPerSquareFoot) &&
                Objects.equals(labourCostPerSquareFoot, product.labourCostPerSquareFoot);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productType, costPerSquareFoot, labourCostPerSquareFoot);
    }
}
