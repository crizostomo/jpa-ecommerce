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
    public void searchByUsingAllExercise() {
        // All products that have always been sold by the same price
        String jpql = "select distinct p from OrderItem oi " +
                "join oi.product p " +
                "where oi.productPrice = ALL " +
                "(select o.productPrice from OrderItem o where o.product = p " +
                "and o.productPrice = oi.productPrice and o.id <> oi.id)";

        TypedQuery<Product> typedQuery = entityManager.createQuery(jpql, Product.class);

        List<Product> list = typedQuery.getResultList();
        Assert.assertFalse(list.isEmpty());

        list.forEach(obj -> System.out.println("ID: " + obj.getId()));
    }

    @Test
    public void searchByUsingAny() {
//         All products that have been sold by a price different from the current one
        String jpql = "select p from Product p where " +
                "p.price <> ANY (select productPrice from OrderItem " +
                "where product = p)";
//        String jpql = "select p from Product p where " +
//                "p.price <> SOME (select productPrice from OrderItem " +
//                "where product = p)";

//         All products that have been sold at least once by the current price
//        String jpql = "select p from Product p where " +
//                "p.price = ANY (select productPrice from OrderItem " +
//                "where product = p)";

        TypedQuery<Product> typedQuery = entityManager.createQuery(jpql, Product.class);

        List<Product> list = typedQuery.getResultList();
        Assert.assertFalse(list.isEmpty());

        list.forEach(obj -> System.out.println("ID: " + obj.getId()));
    }

    @Test
    public void searchByUsingAll() {
//         All products that have not been sold after the price increased
        String jpql = "select p from Product p where " +
                "p.price > ALL (select productPrice from OrderItem " +
                "where product = p)";
//        String jpql = "select p from Product p where " +
//                "p.price > (select max(productPrice) from OrderItem " +
//                "where product = p)";

//         All products that have been sold by the current price
//        String jpql = "select p from Product p where " +
//                "p.price = ALL (select productPrice from OrderItem " +
//                "where product = p)";

        TypedQuery<Product> typedQuery = entityManager.createQuery(jpql, Product.class);

        List<Product> list = typedQuery.getResultList();
        Assert.assertFalse(list.isEmpty());

        list.forEach(obj -> System.out.println("ID: " + obj.getId()));
    }

    @Test
    public void searchByUsingExistsExercise() {
        String jpql = "select p from Product p " +
                "where exists " +
                "(select 1 from OrderItem where product = p and productPrice <> p.price)";

        TypedQuery<Product> typedQuery = entityManager.createQuery(jpql, Product.class);

        List<Product> list = typedQuery.getResultList();
        Assert.assertFalse(list.isEmpty());

        list.forEach(obj -> System.out.println("ID: " + obj.getId()));
    }

    @Test
    public void searchSubqueriesExercise() {
        String jpql = "select c from Client c where " +
                "(select count(client) from Order where client = c) >= 2";

        TypedQuery<Client> typedQuery = entityManager.createQuery(jpql, Client.class);

        List<Client> list = typedQuery.getResultList();
        Assert.assertFalse(list.isEmpty());

        list.forEach(obj -> System.out.println("ID: " + obj.getId()));
    }

    @Test
    public void searchByUsingInExercise() {
        String jpql = "select o from Order o " +
                "where o.id in " +
                "(select o2.id from OrderItem oi2 " +
                "join oi2.order o2 join oi2.product pro2 join pro2.categories c2 where c2.id = 2)";

        TypedQuery<Order> typedQuery = entityManager.createQuery(jpql, Order.class);

        List<Order> list = typedQuery.getResultList();
        Assert.assertFalse(list.isEmpty());

        list.forEach(obj -> System.out.println("ID: " + obj.getId()));
    }

    @Test
    public void searchByUsingExists() {
        String jpql = "select p from Product p " +
                "where exists (select 1 from OrderItem oi2 " +
                "join oi2.product p2 " +
                "where p2 = p) ";

        TypedQuery<Product> typedQuery = entityManager.createQuery(jpql, Product.class);
        List<Product> list = typedQuery.getResultList();
        Assert.assertFalse(list.isEmpty());

        list.forEach(obj -> System.out.println("ID: " + obj.getId()));
    }

    @Test
    public void searchByUsingInVersion1() {
//        String jpql = "select distinct o from Order o " +
//                "join o.orderItems oi " +
//                "join oi.product pro " +
//                "where pro.price > 100 ";
        String jpql = "select o from Order o " +
                "where o.id in (select o2.id from OrderItem oi2 " +
                "join oi2.order o2 " +
                "join oi2.product pro2 " +
                "where pro2.price > 100) ";

        TypedQuery<Order> typedQuery = entityManager.createQuery(jpql, Order.class);
        List<Order> list = typedQuery.getResultList();
        Assert.assertFalse(list.isEmpty());

        list.forEach(obj -> System.out.println("ID: " + obj.getId()));
    }

    @Test
    public void searchByUsingIn() { // It does not repeat the order here in this test
        String jpql = "select o from Order o " +
                "join o.orderItems oi " +
                "join oi.product pro " +
                "where pro.price > 100 ";

        TypedQuery<Order> typedQuery = entityManager.createQuery(jpql, Order.class);
        List<Order> list = typedQuery.getResultList();
        Assert.assertFalse(list.isEmpty());

        list.forEach(obj -> System.out.println("ID: " + obj.getId()));
    }

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
