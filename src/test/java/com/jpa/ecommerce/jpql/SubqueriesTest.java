package com.jpa.ecommerce.jpql;

import com.jpa.ecommerce.EntityManagerTest;
import com.jpa.ecommerce.model.Client;
import com.jpa.ecommerce.model.Order;
import com.jpa.ecommerce.model.Product;
import jakarta.persistence.TypedQuery;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class SubqueriesTest extends EntityManagerTest {

    @Test
    public void searchSubqueries() {
// Good customers Version 2
        String jpql = "select c from Client c where " +
                "500 < (select sum(o.total) from Order o where o.client = c)";
        TypedQuery<Client> typedQuery = entityManager.createQuery(jpql, Client.class);
        List<Client> list = typedQuery.getResultList();
        Assert.assertFalse(list.isEmpty());

        list.forEach(obj -> System.out.println("ID: " + obj.getId() + ", Price: " + obj.getName()));

// Good customers Version 1
//        String jpql = "select c from Client c where " +
//                "500 < (select sum(o.total) from c.orders o)";
//        TypedQuery<Client> typedQuery = entityManager.createQuery(jpql, Client.class);
//        List<Client> list = typedQuery.getResultList();
//        Assert.assertFalse(list.isEmpty());
//
//        list.forEach(obj -> System.out.println("ID: " + obj.getId() + ", Price: " + obj.getName()));

// All products above the sales average
//        String jpql = "select o from Order o where " +
//                "o.total > (select avg(total) from Order o2)"; // o2 is optional
//        TypedQuery<Order> typedQuery = entityManager.createQuery(jpql, Order.class);
//        List<Order> list = typedQuery.getResultList();
//        Assert.assertFalse(list.isEmpty());
//
//        list.forEach(p -> System.out.println("ID: " + p.getId() + ", Price: " + p.getTotal()));

// The most expensive product
//        String jpql = "select p from Product p where " +
//                "p.price = (select max(price) from Product p2)"; // p2 is optional
//
//        TypedQuery<Product> typedQuery = entityManager.createQuery(jpql, Product.class);
//        List<Product> list = typedQuery.getResultList();
//        Assert.assertFalse(list.isEmpty());
//
//        list.forEach(p -> System.out.println("ID: " + p.getId() + ", Price: " + p.getPrice()));
    }
}
