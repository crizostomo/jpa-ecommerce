package com.jpa.ecommerce.jpql;

import com.jpa.ecommerce.EntityManagerTest;
import com.jpa.ecommerce.model.Order;
import com.jpa.ecommerce.model.OrderStatus;
import jakarta.persistence.TypedQuery;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class LogicalOperatorsTest extends EntityManagerTest {

    @Test
    public void usingOperatorsWithStatusAndParenthesis() {
        String jpql = "select o from Order o " +
                "where (o.status = :status1 or o.status = :status2) and o.total > :total";

        TypedQuery<Order> typedQuery = entityManager.createQuery(jpql, Order.class);
        typedQuery.setParameter("status1", OrderStatus.WAITING);
        typedQuery.setParameter("status2", OrderStatus.PAID);
        typedQuery.setParameter("total", 100);

        List<Order> list = typedQuery.getResultList();
        Assert.assertFalse(list.isEmpty());
    }

    @Test
    public void usingOperatorsWithStatus() {
        String jpql = "select o from Order o " +
                "where o.status = :status1 or o.status = :status2";

        TypedQuery<Order> typedQuery = entityManager.createQuery(jpql, Order.class);
        typedQuery.setParameter("status1", OrderStatus.WAITING);
        typedQuery.setParameter("status2", OrderStatus.PAID);

        List<Order> list = typedQuery.getResultList();
        Assert.assertFalse(list.isEmpty());
    }

    @Test
    public void usingOperatorsWithStatusPriceId() {
        String jpql = "select o from Order o " +
                "where o.total > :price and o.status = :status and o.client.id = :id";

        TypedQuery<Order> typedQuery = entityManager.createQuery(jpql, Order.class);
        typedQuery.setParameter("price", 499);
        typedQuery.setParameter("status", OrderStatus.WAITING);
        typedQuery.setParameter("id", 1);

        List<Order> list = typedQuery.getResultList();
        Assert.assertFalse(list.isEmpty());
    }
}
