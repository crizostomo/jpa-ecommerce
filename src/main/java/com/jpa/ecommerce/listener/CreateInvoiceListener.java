package com.jpa.ecommerce.listener;

import com.jpa.ecommerce.model.Order;
import com.jpa.ecommerce.service.InvoiceService;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

public class CreateInvoiceListener {

    private InvoiceService invoiceService = new InvoiceService();

    @PrePersist
    @PreUpdate
    public void create(Order order) {
        if (order.isPaid() && order.getInvoice() == null) {
            invoiceService.create(order);
        }
    }
}
