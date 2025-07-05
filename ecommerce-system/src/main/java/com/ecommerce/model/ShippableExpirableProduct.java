package com.ecommerce.model;

import com.ecommerce.interfaces.Expirable;
import com.ecommerce.interfaces.Shippable;

import java.time.LocalDate;

public class ShippableExpirableProduct extends Product implements Expirable, Shippable {
    private LocalDate expirationDate;
    private double weight;

    public ShippableExpirableProduct(String name, double price, int quantity,
                                     LocalDate expirationDate, double weight) {
        super(name, price, quantity);
        this.expirationDate = expirationDate;
        this.weight = weight;
    }

    @Override
    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    @Override
    public boolean isExpired() {
        return LocalDate.now().isAfter(expirationDate);
    }

    @Override
    public double getWeight() {
        return weight;
    }

    public void setExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    @Override
    public String toString() {
        return super.toString() + " (Expires: " + expirationDate + ", Weight: " + weight + "kg)";
    }
}
