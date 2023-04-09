package com.jpa.ecommerce.service;

import com.jpa.ecommerce.model.Order;

public class InvoiceService {

    public void create(Order order) {
        System.out.println("Creating invoice: " + order.getId());
    }
}
