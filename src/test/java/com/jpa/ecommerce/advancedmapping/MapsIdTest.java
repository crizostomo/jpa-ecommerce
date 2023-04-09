package com.jpa.ecommerce.advancedmapping;

import com.jpa.ecommerce.EntityManagerTest;
import com.jpa.ecommerce.model.*;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.Date;

public class MapsIdTest extends EntityManagerTest {

    @Test
    public void insertPayment() {
        Order order = entityManager.find(Order.class, 1);

        Invoice invoice = new Invoice();
        invoice.setOrder(order);
        invoice.setIssuingDate(new Date());
        invoice.setXml("<xml/>");

        entityManager.getTransaction().begin();
        entityManager.persist(invoice);
        entityManager.getTransaction().commit();

        entityManager.clear();

        Invoice invoiceVerification = entityManager.find(Invoice.class, invoice.getId());
        Assert.assertNotNull(invoiceVerification);
        Assert.assertEquals(order.getId(), invoiceVerification.getId());
    }

    @Test
    public void insertOrderItem() {
        Client client = entityManager.find(Client.class, 1);
        Product product = entityManager.find(Product.class, 1);

        Order order = new Order();
        order.setClient(client);
        order.setCreationDate(LocalDateTime.now());
        order.setStatus(OrderStatus.WAITING);
        order.setTotal(product.getPrice());

        OrderItem orderItem = new OrderItem();
        orderItem.setId(new OrderItemId());
        orderItem.setOrder(order);
        orderItem.setProduct(product);
        orderItem.setProductPrice(product.getPrice());
        orderItem.setQuantity(1);

        entityManager.getTransaction().begin();
        entityManager.persist(order);
        entityManager.persist(orderItem);
        entityManager.getTransaction().commit();

        entityManager.clear();

        OrderItem orderItemVerification = entityManager.find(
                OrderItem.class, new OrderItemId(order.getId(), product.getId()));
        Assert.assertNotNull(orderItemVerification);
    }
}