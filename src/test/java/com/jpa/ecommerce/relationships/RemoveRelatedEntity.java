package com.jpa.ecommerce.relationships;

import com.jpa.ecommerce.EntityManagerTest;
import com.jpa.ecommerce.model.Order;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

public class RemoveRelatedEntity extends EntityManagerTest {

    @Test
    public void removeRelatedEntity() {
        Order order = entityManager.find(Order.class, 1);

        Assert.assertFalse(order.getOrderItems().isEmpty());

        entityManager.getTransaction().begin();
        order.getOrderItems().forEach(i -> entityManager.remove(i)); //remove all itens
        entityManager.remove(order);
        entityManager.getTransaction().commit();

        entityManager.clear();

        Order verificationOrder = entityManager.find(Order.class, 1);
        Assert.assertNull(verificationOrder);
    }
}
