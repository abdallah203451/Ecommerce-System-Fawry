package com.ecommerce.model;

import com.ecommerce.interfaces.Expirable;
import com.ecommerce.interfaces.Shippable;

import java.util.ArrayList;
import java.util.List;

public class Cart {
    private List<CartItem> items;

    public Cart() {
        this.items = new ArrayList<>();
    }

    public void addItem(Product product, int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be positive");
        }
        if (!product.isInStock(quantity)) {
            throw new IllegalArgumentException("Insufficient stock for " + product.getName());
        }

        for (CartItem item : items) {
            if (item.getProduct().equals(product)) {
                int newQuantity = item.getQuantity() + quantity;
                if (!product.isInStock(newQuantity)) {
                    throw new IllegalArgumentException("Insufficient stock for " + product.getName());
                }
                item.setQuantity(newQuantity);
                return;
            }
        }

        items.add(new CartItem(product, quantity));
    }

    public void removeItem(Product product) {
        items.removeIf(item -> item.getProduct().equals(product));
    }

    public void clear() {
        items.clear();
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }

    public List<CartItem> getItems() {
        return new ArrayList<>(items);
    }

    public double getSubtotal() {
        return items.stream().mapToDouble(CartItem::getTotalPrice).sum();
    }

    public List<Shippable> getShippableItems() {
        List<Shippable> shippableItems = new ArrayList<>();
        for (CartItem item : items) {
            if (item.getProduct() instanceof Shippable) {
                for (int i = 0; i < item.getQuantity(); i++) {
                    shippableItems.add((Shippable) item.getProduct());
                }
            }
        }
        return shippableItems;
    }

    public void validateItems() throws Exception {
        if (isEmpty()) {
            throw new com.ecommerce.exception.EmptyCartException("Cart is empty");
        }

        for (CartItem item : items) {
            Product product = item.getProduct();

            if (product instanceof Expirable && ((Expirable) product).isExpired()) {
                throw new com.ecommerce.exception.ProductExpiredException(
                        "Product " + product.getName() + " has expired");
            }

            if (!product.isInStock(item.getQuantity())) {
                throw new com.ecommerce.exception.OutOfStockException(
                        "Product " + product.getName() + " is out of stock");
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Cart Items:\n");
        for (CartItem item : items) {
            sb.append("  ").append(item.toString()).append("\n");
        }
        sb.append("Subtotal: ").append(getSubtotal());
        return sb.toString();
    }
}
