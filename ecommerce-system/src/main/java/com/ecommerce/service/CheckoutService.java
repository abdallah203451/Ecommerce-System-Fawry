package com.ecommerce.service;

import com.ecommerce.interfaces.Shippable;
import com.ecommerce.model.Cart;
import com.ecommerce.model.CartItem;
import com.ecommerce.model.Customer;

import java.util.List;

public class CheckoutService {
    private ShippingService shippingService;

    public CheckoutService() {
        this.shippingService = new ShippingService();
    }

    public void checkout(Customer customer, Cart cart) throws Exception {
        cart.validateItems();

        List<Shippable> shippableItems = cart.getShippableItems();
        double subtotal = cart.getSubtotal();
        double shippingFees = shippingService.calculateShippingFee(shippableItems);
        double totalAmount = subtotal + shippingFees;


        // Check customer balance
        if (customer.getBalance() < totalAmount) {
            throw new com.ecommerce.exception.InsufficientBalanceException(
                    "Customer balance (" + customer.getBalance() +
                            ") is insufficient for total amount (" + totalAmount + ")");
        }

        if (!shippableItems.isEmpty()) {
            shippingService.processShipment(shippableItems);
        }

        customer.decreaseAmount(totalAmount);

        for (CartItem item : cart.getItems()) {
            item.getProduct().decreaseQuantity(item.getQuantity());
        }

        printCheckoutReceipt(cart, subtotal, shippingFees, totalAmount);

        System.out.println("Customer balance after checkout: " + customer.getBalance());

    }

    private void printCheckoutReceipt(Cart cart, double subtotal, double shippingFees, double totalAmount) {
        System.out.println("** Checkout receipt **");

        for (CartItem item : cart.getItems()) {
            System.out.println(item.getQuantity() + "x " + item.getProduct().getName() + " " +
                    (int)item.getTotalPrice());
        }

        System.out.println("----------------------");
        System.out.println("Subtotal " + (int)subtotal);
        System.out.println("Shipping " + (int)shippingFees);
        System.out.println("Amount " + (int)totalAmount);
    }
}
