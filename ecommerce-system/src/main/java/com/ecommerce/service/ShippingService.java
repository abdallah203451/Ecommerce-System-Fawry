package com.ecommerce.service;

import com.ecommerce.interfaces.Shippable;

import java.util.List;

public class ShippingService {
    //assumption: the minimum shipping fee is 5 and every KG cost 10
    private static final double SHIPPING_FEE_PER_KG = 10.0;
    private static final double BASE_SHIPPING_FEE = 5.0;

    public double calculateShippingFee(List<Shippable> items) {
        if (items.isEmpty()) {
            return 0.0;
        }

        double totalWeight = items.stream().mapToDouble(Shippable::getWeight).sum();
        return BASE_SHIPPING_FEE + (totalWeight * SHIPPING_FEE_PER_KG);
    }

    public void processShipment(List<Shippable> items) {
        if (items.isEmpty()) {
            return;
        }

        System.out.println("** Shipment notice **");

        java.util.Map<String, Double> itemWeights = new java.util.LinkedHashMap<>();
        java.util.Map<String, Long> itemCounts = new java.util.LinkedHashMap<>();

        for (Shippable item : items) {
            itemWeights.merge(item.getName(), item.getWeight(), Double::sum);
            itemCounts.merge(item.getName(), 1L, Long::sum);
        }

        itemWeights.forEach((name, weight) -> {
            long count = itemCounts.get(name);
            System.out.println(count + "x " + name + " " + (int)(weight * 1000) + "g");
        });

        double totalWeight = items.stream().mapToDouble(Shippable::getWeight).sum();
        System.out.println("Total package weight " + totalWeight + "kg");
        System.out.println();
    }
}
