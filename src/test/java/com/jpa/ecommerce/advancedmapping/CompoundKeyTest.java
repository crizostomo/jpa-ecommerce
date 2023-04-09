package com.jpa.ecommerce.advancedmapping;

import com.jpa.ecommerce.EntityManagerTest;
import com.jpa.ecommerce.model.*;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDateTime;

public class CompoundKeyTest extends EntityManagerTest {

    @Test
    public void saveItem() {
        entityManager.getTransaction().begin();

        Client client = entityManager.find(Client.class, 1);
        Product product = entityManager.find(Product.class, 1);

        Order order = new Order();
        order.setClient(client);
        order.setCreationDate(LocalDateTime.now());
        order.setStatus(OrderStatus.WAITING);
        order.setTotal(product.getPrice());

        entityManager.persist(order);

        entityManager.flush();

        OrderItem orderItem = new OrderItem();
        orderItem.setId(new OrderItemId(order.getId(), product.getId()));
        orderItem.setOrder(order);
        orderItem.setProduct(product);
        orderItem.setProductPrice(product.getPrice());
        orderItem.setQuantity(1);

        entityManager.persist(orderItem);

        entityManager.getTransaction().commit();

        entityManager.clear();

        Order orderVerification = entityManager.find(Order.class, order.getId());
        Assert.assertNotNull(orderVerification);
        Assert.assertFalse(orderVerification.getOrderItems().isEmpty());
    }

    @Test
    public void searchItem() {
        OrderItem orderItem = entityManager.find(
                OrderItem.class, new OrderItemId(1, 1));

        Assert.assertNotNull(orderItem);
    }
}