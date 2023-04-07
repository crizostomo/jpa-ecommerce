package com.jpa.ecommerce.entitymanager;

import com.jpa.ecommerce.EntityManagerTest;
import com.jpa.ecommerce.model.Order;
import com.jpa.ecommerce.model.OrderStatus;
import org.junit.Assert;
import org.junit.Test;

public class FlushTest extends EntityManagerTest {

    @Test(expected = Exception.class)
    public void callFlush() {
        try {
            entityManager.getTransaction().begin();

            Order order = entityManager.find(Order.class, 1);
            order.setStatus(OrderStatus.PAID);

            entityManager.flush();

            if (order.getPayment() == null) {
                throw new RuntimeException("Order was not paid");
            }

//            A consult obligates the JPA to synchronize what it has in memory
//            Order paidOrder = entityManager
//                    .createQuery("select o from Order o where o.id = 1", Order.class)
//                    .getSingleResult();
//            Assert.assertEquals(order.getStatus(), paidOrder.getStatus());

            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            throw e;
        }
    }
}
