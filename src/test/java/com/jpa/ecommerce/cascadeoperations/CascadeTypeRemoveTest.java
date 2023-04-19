package com.jpa.ecommerce.cascadeoperations;

import com.jpa.ecommerce.EntityManagerTest;
import com.jpa.ecommerce.model.Order;
import com.jpa.ecommerce.model.OrderItem;
import com.jpa.ecommerce.model.OrderItemId;
import org.junit.Assert;
import org.junit.Test;

public class CascadeTypeRemoveTest extends EntityManagerTest {

//    @Test
    public void removeOrderAndItems() {
        Order order = entityManager.find(Order.class, 1);

        entityManager.getTransaction().begin();
//        order.getOrderItems().forEach(i -> entityManager.remove(i)); // A way to remove without using Cascade
        entityManager.remove(order); // It is needed CascadeType.REMOVE in the attribute "items".
        entityManager.getTransaction().commit();

        entityManager.clear();

        Order orderVerification = entityManager.find(Order.class, order.getId());
        Assert.assertNull(orderVerification);
    }

//     @Test
    public void removeOrderItemAndOrder() {
        OrderItem orderItem = entityManager.find(
                OrderItem.class, new OrderItemId(1, 1));

        entityManager.getTransaction().begin();
        entityManager.remove(orderItem); // It is needed CascadeType.REMOVE in the attribute "order".
        entityManager.getTransaction().commit();

        entityManager.clear();

        Order orderVerification = entityManager.find(Order.class, orderItem.getOrder().getId());
        Assert.assertNull(orderVerification);
    }
}