package com.jpa.ecommerce.relationships;

import com.jpa.ecommerce.EntityManagerTest;
import com.jpa.ecommerce.model.Client;
import com.jpa.ecommerce.model.Order;
import com.jpa.ecommerce.model.OrderStatus;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class RelationshipOneToManyTest extends EntityManagerTest {

    @Test
    public void verifyRelationship() {
        Client client = entityManager.find(Client.class, 1);

        Order order = new Order();
        order.setStatus(OrderStatus.WAITING);
        order.setCreationDate(LocalDateTime.now());
        order.setClient(client);
        order.setTotal(BigDecimal.TEN);

        entityManager.getTransaction().begin();
        entityManager.persist(order);
        entityManager.getTransaction().commit();

        entityManager.clear();

        Client clientVerification = entityManager.find(Client.class, client.getId());
        Assert.assertFalse(clientVerification.getOrders().isEmpty());
    }
}
