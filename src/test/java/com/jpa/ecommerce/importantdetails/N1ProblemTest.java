package com.jpa.ecommerce.importantdetails;

import com.jpa.ecommerce.EntityManagerTest;
import com.jpa.ecommerce.model.Order;
import jakarta.persistence.EntityGraph;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class N1ProblemTest extends EntityManagerTest {

    @Test
    public void solveByUsingEntityGraph() {
        EntityGraph<Order> entityGraph = entityManager.createEntityGraph(Order.class);
        entityGraph.addAttributeNodes("client", "invoice", "payment");
        List<Order> list = entityManager
                .createQuery("select o from Order o ", Order.class)
                .setHint("jakarta.persistence.loadgraph", entityGraph)
                .getResultList();

        Assert.assertFalse(list.isEmpty());
    }

    @Test
    public void solveByUsingFetch() {
        List<Order> list = entityManager
                .createQuery("select o from Order o " +
                        "join fetch o.client o " +
                        "join fetch o.payment pag " +
                        "join fetch o.invoice inv", Order.class)
                .getResultList();

        Assert.assertFalse(list.isEmpty());
    }
}
