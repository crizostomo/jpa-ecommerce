package com.jpa.ecommerce.entitymanager;

import com.jpa.ecommerce.EntityManagerTest;
import com.jpa.ecommerce.model.Order;
import com.jpa.ecommerce.model.OrderStatus;
import org.junit.Test;

public class ManagingTransactionTest extends EntityManagerTest {

    @Test(expected = Exception.class)
    public void openCloseCancelTransaction() {
        try {
            entityManager.getTransaction().begin();
            businessModel();
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            throw e;
        }
    }

    public void businessModel() {
        Order order = entityManager.find(Order.class, 1);
        order.setStatus(OrderStatus.PAID);

        if (order.getPayment() != null) {
            throw new RuntimeException("Order was not paid");
        }
    }
}
