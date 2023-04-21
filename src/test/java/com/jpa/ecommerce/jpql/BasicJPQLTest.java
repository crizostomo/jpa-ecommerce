package com.jpa.ecommerce.jpql;

import com.jpa.ecommerce.EntityManagerTest;
import com.jpa.ecommerce.model.Order;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class BasicJPQLTest extends EntityManagerTest {

    @Test
    public void searchByIdentifier() {

        TypedQuery<Order> typedQuery = entityManager.createQuery("select o from Order o where o.id = 1", Order.class);

        Order order = typedQuery.getSingleResult();
        Assert.assertNotNull(order);

        List<Order> list = typedQuery.getResultList();
        Assert.assertFalse(list.isEmpty());
    }

    @Test
    public void showQueriesDifference() {
        String jpql = "select o from Order o where o.id = 1";

        TypedQuery<Order> typedQuery = entityManager.createQuery(jpql, Order.class);
        Order orderTypedQuery = typedQuery.getSingleResult();
        Assert.assertNotNull(orderTypedQuery);

        Query query = entityManager.createQuery(jpql);
        Order orderQuery = (Order) query.getSingleResult();
        Assert.assertNotNull(orderQuery);
    }
}
