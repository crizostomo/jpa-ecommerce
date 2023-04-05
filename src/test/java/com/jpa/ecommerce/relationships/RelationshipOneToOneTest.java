package com.jpa.ecommerce.relationships;

import com.jpa.ecommerce.EntityManagerTest;
import com.jpa.ecommerce.model.CardPayment;
import com.jpa.ecommerce.model.Invoice;
import com.jpa.ecommerce.model.Order;
import com.jpa.ecommerce.model.PaymentStatus;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.util.Date;

public class RelationshipOneToOneTest extends EntityManagerTest {

    @Test
    public void verifyRelationship() {
        Order order = entityManager.find(Order.class, 1);
        CardPayment cardPayment = new CardPayment();

        cardPayment.setNumber("1234");
        cardPayment.setStatus(PaymentStatus.PROCESSING);
        cardPayment.setOrder(order);

        entityManager.getTransaction().begin();
        entityManager.persist(cardPayment);
        entityManager.getTransaction().commit();

        entityManager.clear();

        Order orderVerification = entityManager.find(Order.class, order.getId());
        Assert.assertNotNull(orderVerification.getPayment());
    }

    @Test
    public void verifyRelationshipOrderInvoice() {
        Order order = entityManager.find(Order.class, 1);
        Invoice invoice = new Invoice();

        invoice.setXml("TEST1234");
        invoice.setIssuingDate(new Date());
        invoice.setOrder(order);

        entityManager.getTransaction().begin();
        entityManager.persist(invoice);
        entityManager.getTransaction().commit();

        entityManager.clear();

        Order orderVerification = entityManager.find(Order.class, order.getId());
        Assert.assertNotNull(orderVerification.getInvoice());
    }
}
