package com.jpa.ecommerce.listener;

import com.jpa.ecommerce.model.Order;
import com.jpa.ecommerce.service.InvoiceService;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

public class generateInvoiceListener {

    private InvoiceService notaFiscalService = new InvoiceService();

    @PrePersist
    @PreUpdate
    public void generate(Order order) {
        if (order.isPaid() && order.getInvoice() == null) {
            notaFiscalService.create(order);
        }
    }
}