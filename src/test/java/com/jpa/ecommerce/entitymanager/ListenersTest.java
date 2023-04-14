package com.jpa.ecommerce.entitymanager;

import com.jpa.ecommerce.EntityManagerTest;
import com.jpa.ecommerce.model.Client;
import com.jpa.ecommerce.model.Order;
import com.jpa.ecommerce.model.OrderStatus;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ListenersTest extends EntityManagerTest {

    @Test
    public void setCallbacks() {
        Client client = entityManager.find(Client.class, 1);

        Order order = new Order();
        order.setCreationDate(LocalDateTime.now());

        order.setClient(client);
        order.setStatus(OrderStatus.WAITING);
        order.setTotal(BigDecimal.TEN);

        entityManager.getTransaction().begin();

        entityManager.persist(order);
        entityManager.flush();

        order.setStatus(OrderStatus.PAID);
        entityManager.getTransaction().commit();

        entityManager.clear();

        Order orderVerification = entityManager.find(Order.class, order.getId());
        Assert.assertNotNull(orderVerification.getCreationDate());
        Assert.assertNotNull(orderVerification.getLastUpdateDate());
    }
}
