package com.jpa.ecommerce.importantdetails;

import com.jpa.ecommerce.EntityManagerTest;
import com.jpa.ecommerce.model.Order;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class OneOneLazyTest extends EntityManagerTest {

//    @Test // To execute this test, it is needed to uncomment some lines in Order.class (this test has some inconsistencies though)
    public void showProblem() {
        System.out.println("SEARCHING AN ORDER:");
        Order order = entityManager.find(Order.class, 1);
        Assert.assertNotNull(order);

        System.out.println("----------------------------------------------------");

        System.out.println("SEARCHING AN ORDER LIST:");
        List<Order> list = entityManager
                .createQuery("select o from order o " +
                        "join fetch o.invoice", Order.class)
                .getResultList();
        Assert.assertFalse(list.isEmpty());
    }
}