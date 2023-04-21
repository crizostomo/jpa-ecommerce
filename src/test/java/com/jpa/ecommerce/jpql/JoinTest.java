package com.jpa.ecommerce.jpql;

import com.jpa.ecommerce.EntityManagerTest;
import com.jpa.ecommerce.model.Order;
import jakarta.persistence.TypedQuery;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class JoinTest extends EntityManagerTest {

    @Test
    public void usingLeftJoinWithFilter() {
        String jpql = "select o from Order o left join o.payment pay on pay.status = 'PROCESSING'";

        TypedQuery<Order> typedQuery = entityManager.createQuery(jpql, Order.class);
        List<Order> list = typedQuery.getResultList();
        Assert.assertFalse(list.isEmpty());
    }

    @Test
    public void usingLeftJoin() {
        String jpql = "select o from Order o left join o.payment pay";

        TypedQuery<Order> typedQuery = entityManager.createQuery(jpql, Order.class);
        List<Order> list = typedQuery.getResultList();
        Assert.assertFalse(list.isEmpty());
    }

    @Test
    public void usingJoin() {
        String jpql = "select o from Order o inner join o.payment pay";

        TypedQuery<Order> typedQuery = entityManager.createQuery(jpql, Order.class);
        List<Order> list = typedQuery.getResultList();
        Assert.assertTrue(list.size() == 1);
    }

    @Test
    public void usingJoinWithArray() {
        String jpql = "select o, pay from Order o inner join o.payment pay";
//        String jpql = "select o, i from Order o inner join o.orderItems i";
//        String jpql = "select o from Order o inner join o.orderItems i where i.productPrice > 10";

        TypedQuery<Object[]> typedQuery = entityManager.createQuery(jpql, Object[].class);
        List<Object[]> list = typedQuery.getResultList();
//        Assert.assertTrue(list.size() == 3); // using orderItems
        Assert.assertTrue(list.size() == 1);
        Assert.assertFalse(list.isEmpty());
    }

    @Test
    public void usingJoinWithArrayAndFilter() {
        String jpql = "select o, pay from Order o inner join o.payment pay where pay.status = 'PROCESSING'";

        TypedQuery<Object[]> typedQuery = entityManager.createQuery(jpql, Object[].class);
        List<Object[]> list = typedQuery.getResultList();
        Assert.assertTrue(list.size() == 1);
    }
}
