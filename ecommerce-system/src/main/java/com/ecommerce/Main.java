package com.ecommerce;

import com.ecommerce.model.*;
import com.ecommerce.service.CheckoutService;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        try {
            CheckoutService checkoutService = new CheckoutService();

            //assumption: entering weight in KG
            ShippableExpirableProduct cheese = new ShippableExpirableProduct(
                    "Cheese", 100.0, 10, LocalDate.now().plusDays(30), 0.2);

            ShippableExpirableProduct biscuits = new ShippableExpirableProduct(
                    "Biscuits", 150.0, 5, LocalDate.now().plusDays(60), 0.7);

            ShippableProduct tv = new ShippableProduct("TV", 500.0, 3, 15.0);

            Product scratchCard = new Product("Mobile scratch card", 25.0, 20);

            Customer customer = new Customer("Abdallah Ashraf", 1200.0);

            Cart cart = new Cart();
            cart.addItem(cheese, 2);
            cart.addItem(biscuits, 1);
            cart.addItem(scratchCard, 1);


            System.out.println("=== EXAMPLE 1: Successful Checkout ===");
            System.out.println("Customer balance before checkout: " + customer.getBalance());

            checkoutService.checkout(customer, cart);

            System.out.println();

            cart.clear();


            System.out.println("=== EXAMPLE 2: Empty Cart Error ===");
            try {
                checkoutService.checkout(customer, cart);
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
            System.out.println();


            System.out.println("=== EXAMPLE 3: Insufficient Balance Error ===");

            ShippableProduct expensiveTV = new ShippableProduct("Expensive TV", 2000.0, 1, 20.0);
            cart.addItem(expensiveTV, 1);

            try {
                checkoutService.checkout(customer, cart);
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
            cart.clear();
            System.out.println();


            System.out.println("=== EXAMPLE 4: Expired Product Error ===");

            ShippableExpirableProduct expiredCheese = new ShippableExpirableProduct(
                    "Expired Cheese", 100.0, 5, LocalDate.now().minusDays(1), 0.2);
            cart.addItem(expiredCheese, 1);

            try {
                checkoutService.checkout(customer, cart);
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
            cart.clear();
            System.out.println();


            System.out.println("=== EXAMPLE 5: Out of Stock Error ===");
            try {
                cart.addItem(tv, 5);
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
            System.out.println();


            System.out.println("=== EXAMPLE 6: Another Successful Checkout ===");

            cart.addItem(tv, 1);
            cart.addItem(scratchCard, 2);

            checkoutService.checkout(customer, cart);

        } catch (Exception e) {
            System.err.println("Unexpected error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}