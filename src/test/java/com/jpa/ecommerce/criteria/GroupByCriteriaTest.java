package com.jpa.ecommerce.criteria;

import com.jpa.ecommerce.EntityManagerTest;
import com.jpa.ecommerce.model.Order;
import com.jpa.ecommerce.model.*;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class GroupByCriteriaTest extends EntityManagerTest {

    @Test
    public void groupingResultsByUsingFunctions() {
//        Total amount of sales per month.
//        String jpql = "select concat(year(o.creationDate), '/', function('monthname', o.creationDate)), sum(p.total) " +
//                " from Order o " +
//                " group by year(o.creationDate), month(o.creationDate) ";

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Object[]> criteriaQuery = criteriaBuilder.createQuery(Object[].class);
        Root<Order> root = criteriaQuery.from(Order.class);

        Expression<Integer> creationYearOrder = criteriaBuilder
                .function("year", Integer.class, root.get(Order_.creationDate));
        Expression<Integer> creationMonthOrder = criteriaBuilder
                .function("month", Integer.class, root.get(Order_.creationDate));
        Expression<String> creationMonthNameOrder = criteriaBuilder
                .function("monthname", String.class, root.get(Order_.creationDate));

        Expression<String> anoMesConcat = criteriaBuilder.concat(criteriaBuilder.concat(creationYearOrder.as(String.class), "/"), creationMonthNameOrder);

        criteriaQuery.multiselect(
                anoMesConcat,
                criteriaBuilder.sum(root.get(Order_.total))
        );

        criteriaQuery.groupBy(creationYearOrder, creationMonthOrder);

        TypedQuery<Object[]> typedQuery = entityManager.createQuery(criteriaQuery);
        List<Object[]> list = typedQuery.getResultList();

        list.forEach(arr -> System.out.println("Year/Month: " + arr[0] + ", Sum: " + arr[1]));
    }

    @Test
    public void groupResultExercise() {
        // Total amount of sales by client
        // String jpql = "select c.name, sum(oi.productPrice) from OrderItem oi " +
        // "join oi.order o join o.client c " +
        // "group by c.id";

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Object[]> criteriaQuery = criteriaBuilder.createQuery(Object[].class);
        Root<OrderItem> root = criteriaQuery.from(OrderItem.class);
        Join<OrderItem, Order> orderItemOrderJoin = root.join(OrderItem_.order);
        Join<Order, Client> orderClientJoin = orderItemOrderJoin.join(Order_.client);

        criteriaQuery.multiselect(orderClientJoin.get(Client_.name),
                criteriaBuilder.sum(root.get(OrderItem_.productPrice))
        );

        criteriaQuery.groupBy(orderClientJoin.get(Category_.id));

        TypedQuery<Object[]> typedQuery = entityManager.createQuery(criteriaQuery);
        List<Object[]> list = typedQuery.getResultList();
        Assert.assertFalse(list.isEmpty());

        list.forEach(arr -> System.out.println("Client Name: " + arr[0] + ", Sum: " + arr[1]));
    }

    @Test
    public void groupResult2() {
        // Total amount of sales by category
        // String jpql = "select c.name, sum(oi.productPrice) from OrderItem oi " +
        // "join oi.product pro join pro.categories c " +
        // "group by c.id";

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Object[]> criteriaQuery = criteriaBuilder.createQuery(Object[].class);
        Root<OrderItem> root = criteriaQuery.from(OrderItem.class);
        Join<OrderItem, Product> orderItemProductJoin = root.join(OrderItem_.product);
        Join<Product, Category> productCategoryJoin = orderItemProductJoin.join(Product_.categories);

        criteriaQuery.multiselect(productCategoryJoin.get(Category_.name),
                criteriaBuilder.sum(root.get(OrderItem_.productPrice))
        );

        criteriaQuery.groupBy(productCategoryJoin.get(Category_.id));

        TypedQuery<Object[]> typedQuery = entityManager.createQuery(criteriaQuery);
        List<Object[]> list = typedQuery.getResultList();
        Assert.assertFalse(list.isEmpty());

        list.forEach(arr -> System.out.println("Category Name: " + arr[0] + ", Sum: " + arr[1]));
    }

    @Test
    public void groupResult1() {
        // Quantity of products by category
        // String jpql = "select c.name, count (p.id) from Category c join c.products p group by c.id";

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Object[]> criteriaQuery = criteriaBuilder.createQuery(Object[].class);
        Root<Category> root = criteriaQuery.from(Category.class);
        Join<Category, Product> categoryProductJoin = root.join(Category_.products, JoinType.LEFT);

        criteriaQuery.multiselect(root.get(Category_.name),
                criteriaBuilder.count(categoryProductJoin.get(Product_.id))
        );

        criteriaQuery.groupBy(root.get(Category_.id));

        TypedQuery<Object[]> typedQuery = entityManager.createQuery(criteriaQuery);
        List<Object[]> list = typedQuery.getResultList();
        Assert.assertFalse(list.isEmpty());

        list.forEach(arr -> System.out.println("Name: " + arr[0] + ", Count: " + arr[1]));
    }
}
