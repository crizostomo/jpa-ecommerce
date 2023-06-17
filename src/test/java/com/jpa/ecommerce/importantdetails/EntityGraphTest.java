package com.jpa.ecommerce.importantdetails;

import com.jpa.ecommerce.EntityManagerTest;
import com.jpa.ecommerce.model.Client;
import com.jpa.ecommerce.model.Order;
import jakarta.persistence.EntityGraph;
import jakarta.persistence.Subgraph;
import jakarta.persistence.TypedQuery;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EntityGraphTest extends EntityManagerTest {

    //    @Test // To execute this test, it is needed to uncomment some lines in Order.class (this test has some inconsistencies though)
    public void searchEssentialOrderAttributesByUsingAnnotationNamedEntityGraph() { //See annotation in the Order.Class
        EntityGraph<?> entityGraph = entityManager.createEntityGraph("Order.essentialData");

//        entityGraph.addSubgraph("payment").addAttributeNodes("status");

        TypedQuery<Order> typedQuery = entityManager.createQuery("select o from Order o", Order.class);
        typedQuery.setHint("jakarta.persistence.fetchgraph", entityGraph);
        List<Order> list = typedQuery.getResultList();
        Assert.assertFalse(list.isEmpty());
    }

    //    @Test // To execute this test, it is needed to uncomment some lines in Order.class (this test has some inconsistencies though)
    public void searchEssentialOrderAttributes02() {
        EntityGraph<Order> entityGraph = entityManager.createEntityGraph(Order.class);
        entityGraph.addAttributeNodes("creationDate", "status", "total"); // We can use Metamodels too, e.g., Order_.creationDate

        Subgraph<Client> clientSubgraph = entityGraph.addSubgraph("client", Client.class); // Here, we wouldn't need to use Client.class
        clientSubgraph.addAttributeNodes("name", "cpf");

        TypedQuery<Order> typedQuery = entityManager.createQuery("select o from Order o", Order.class);
        typedQuery.setHint("jakarta.persistence.fetchgraph", entityGraph);
        List<Order> list = typedQuery.getResultList();
        Assert.assertFalse(list.isEmpty());
    }

    //    @Test // To execute this test, it is needed to uncomment some lines in Order.class (this test has some inconsistencies though)
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
