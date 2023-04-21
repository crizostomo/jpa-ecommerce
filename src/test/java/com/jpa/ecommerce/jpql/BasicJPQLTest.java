package com.jpa.ecommerce.jpql;

import com.jpa.ecommerce.EntityManagerTest;
import com.jpa.ecommerce.model.Client;
import com.jpa.ecommerce.model.Order;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class BasicJPQLTest extends EntityManagerTest {

    @Test
    public void projectTheResult() {
        String jpql = "select id, name from Product";

        TypedQuery<Object[]> typedQuery = entityManager.createQuery(jpql, Object[].class);
        List<Object[]> list = typedQuery.getResultList();

        Assert.assertTrue(list.get(0).length == 2);

        list.forEach(array -> System.out.println(array[0] + ", " + array[1]));
    }

    @Test
    public void selectAnAttributeToReturn() {
        String jpql = "select p.name from Product p where p.id =1";

        TypedQuery<String> typedQuery = entityManager.createQuery(jpql, String.class);
        List<String> list = typedQuery.getResultList();
        Assert.assertTrue(String.class.equals(list.get(0).getClass()));

        String clientJpql = "select o.client from Order o";
        TypedQuery<Client> clientTypedQuery = entityManager.createQuery(clientJpql, Client.class);
        List<Client> clientList = clientTypedQuery.getResultList();
        Assert.assertTrue(Client.class.equals(clientList.get(0).getClass()));
    }

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
