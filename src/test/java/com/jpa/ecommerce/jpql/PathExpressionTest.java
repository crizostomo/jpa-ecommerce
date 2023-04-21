package com.jpa.ecommerce.jpql;

import com.jpa.ecommerce.EntityManagerTest;
import com.jpa.ecommerce.model.Order;
import jakarta.persistence.TypedQuery;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class PathExpressionTest extends EntityManagerTest {

    @Test
    public void searchOrdersWithASpecificProduct() {
        String jpql = "select o from Order o join o.orderItems i where i.id.productId = 1";
//        String jpql = "select o from Order o join o.orderItems i where i.product.id = 1";
//        String jpql = "select o from Order o join o.orderItems i join i.product pro where pro.id = 1";

        TypedQuery<Order> typedQuery = entityManager.createQuery(jpql, Order.class);

        List<Order> list = typedQuery.getResultList();
        Assert.assertTrue(list.size() == 2);
    }

    @Test
    public void usePathExpressions() {
        String jpql = "select o.client.name from Order o";

        TypedQuery<Object[]> typedQuery = entityManager.createQuery(jpql, Object[].class);

        List<Object[]> list = typedQuery.getResultList();
        Assert.assertFalse(list.isEmpty());
    }
}
