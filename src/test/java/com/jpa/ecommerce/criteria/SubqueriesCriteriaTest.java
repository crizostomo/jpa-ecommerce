package com.jpa.ecommerce.criteria;

import com.jpa.ecommerce.EntityManagerTest;
import com.jpa.ecommerce.model.*;
import com.jpa.ecommerce.model.Order;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.List;

public class SubqueriesCriteriaTest extends EntityManagerTest {

    @Test
    public void searchByUsingIN() {
//        String jpql = "select o from Order o " +
//                "join o.orderItems oi " +
//                "join oi.product pro " +
//                "where pro.price > 100 ";

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Order> criteriaQuery = criteriaBuilder.createQuery(Order.class);
        Root<Order> root = criteriaQuery.from(Order.class);

        criteriaQuery.select(root);

        Subquery<Integer> subquery = criteriaQuery.subquery(Integer.class);
        Root<OrderItem> subqueryRoot = subquery.from(OrderItem.class);
        Join<OrderItem, Order> subqueryJoinOrder = subqueryRoot.join(OrderItem_.order);
        Join<OrderItem, Product> subqueryJoinProduct = subqueryRoot.join(OrderItem_.product);
        subquery.select(subqueryJoinOrder.get(Order_.id));
        subquery.where(criteriaBuilder.greaterThan(subqueryJoinProduct.get(Product_.price), new BigDecimal(100)));

        criteriaQuery.where(root.get(Order_.id).in(subquery));

        TypedQuery<Order> typedQuery = entityManager.createQuery(criteriaQuery);

        List<Order> list = typedQuery.getResultList();
        Assert.assertFalse(list.isEmpty());

        list.forEach(obj -> System.out.println("ID: " + obj.getId()));
    }

    @Test
    public void searchSubqueries03() {
// Good customers Version 1
//        String jpql = "select c from Client c where " +
//                "1300 < (select sum(o.total) from c.orders o)";

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Client> criteriaQuery = criteriaBuilder.createQuery(Client.class);
        Root<Client> root = criteriaQuery.from(Client.class);

        criteriaQuery.select(root);

        Subquery<BigDecimal> subquery = criteriaQuery.subquery(BigDecimal.class);
        Root<Order> subqueryRoot = subquery.from(Order.class);
        subquery.select(criteriaBuilder.sum(subqueryRoot.get(Order_.total)));
        subquery.where(criteriaBuilder.equal(root, subqueryRoot.get(Order_.client)));
//        subquery.where(criteriaBuilder.equal(root.get(Client_.id), subqueryRoot.get(Order_.client).get(Client_.id))); // The same as showed above

        criteriaQuery.where(criteriaBuilder.greaterThan(subquery, new BigDecimal(1300)));

        TypedQuery<Client> typedQuery = entityManager.createQuery(criteriaQuery);

        List<Client> list = typedQuery.getResultList();
        Assert.assertFalse(list.isEmpty());

        list.forEach(obj -> System.out.println("ID: " + obj.getId() + ", Name: " + obj.getName()));
    }

    @Test
    public void searchSubqueries02() {
// All products above the sales average
//        String jpql = "select o from Order o where " +
//                "o.total > (select avg(total) from Order o2)"; // o2 is optional

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Order> criteriaQuery = criteriaBuilder.createQuery(Order.class);
        Root<Order> root = criteriaQuery.from(Order.class);

        criteriaQuery.select(root);

        Subquery<BigDecimal> subquery = criteriaQuery.subquery(BigDecimal.class);
        Root<Order> subqueryRoot = subquery.from(Order.class);
        subquery.select(criteriaBuilder.avg(subqueryRoot.get(Order_.total)).as(BigDecimal.class));

        criteriaQuery.where(criteriaBuilder.greaterThan(root.get(Order_.total), subquery));

        TypedQuery<Order> typedQuery = entityManager.createQuery(criteriaQuery);
        List<Order> list = typedQuery.getResultList();
        Assert.assertFalse(list.isEmpty());

        list.forEach(obj -> System.out.println("ID: " + obj.getId() + ", Total: " + obj.getTotal()));
    }

    @Test
    public void searchSubqueries01() {
//         The most expensive product
//        String jpql = "select p from Product p where " +
//                "p.price = (select max(price) from Product p2)"; // p2 is optional

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Product> criteriaQuery = criteriaBuilder.createQuery(Product.class);
        Root<Product> root = criteriaQuery.from(Product.class);

        criteriaQuery.select(root);

        Subquery<BigDecimal> subquery = criteriaQuery.subquery(BigDecimal.class);
        Root<Product> subqueryRoot = subquery.from(Product.class);
        subquery.select(criteriaBuilder.max(subqueryRoot.get(Product_.price)));

        criteriaQuery.where(criteriaBuilder.equal(root.get(Product_.price), subquery));

        TypedQuery<Product> typedQuery = entityManager.createQuery(criteriaQuery);
        List<Product> list = typedQuery.getResultList();
        Assert.assertFalse(list.isEmpty());

        list.forEach(obj -> System.out.println("ID: " + obj.getId() + ", Name: " + obj.getName() + ", Price: " + obj.getPrice()));
    }
}