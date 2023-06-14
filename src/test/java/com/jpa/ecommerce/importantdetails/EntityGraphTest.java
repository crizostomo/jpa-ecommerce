package com.jpa.ecommerce.importantdetails;

import com.jpa.ecommerce.EntityManagerTest;
import com.jpa.ecommerce.model.Order;
import jakarta.persistence.EntityGraph;
import jakarta.persistence.TypedQuery;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EntityGraphTest extends EntityManagerTest {

    @Test
    public void searchEssentialOrderAttributes() {
        EntityGraph<Order> entityGraph = entityManager.createEntityGraph(Order.class);
        entityGraph.addAttributeNodes("creationDate", "status", "total", "client");

        Map<String, Object> properties = new HashMap<>();
//        properties.put("jakarta.persistence.fetchgraph", entityGraph);
        properties.put("jakarta.persistence.loadhgraph", entityGraph);

        Order order = entityManager.find(Order.class, 1, properties);
        Assert.assertNotNull(order);

        TypedQuery<Order> typedQuery = entityManager.createQuery("select o from Order o", Order.class);
        typedQuery.setHint("jakarta.persistence.fetchgraph", entityGraph);
        List<Order> list = typedQuery.getResultList();
        Assert.assertFalse(list.isEmpty());
    }
}
