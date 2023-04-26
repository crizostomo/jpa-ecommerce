package com.jpa.ecommerce.jpql;

import com.jpa.ecommerce.EntityManagerTest;
import com.jpa.ecommerce.model.Order;
import jakarta.persistence.TypedQuery;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;
import java.util.TimeZone;

public class FunctionTest extends EntityManagerTest {

    @Test
    public void applyAggregationFunction() {
        // avg, count, min, max, sum
        String jpql = "select avg (o.total) from Order o";

        TypedQuery<Number> typedQuery = entityManager.createQuery(jpql, Number.class); //Number works for avg-count-min-max-sum

        List<Number> list = typedQuery.getResultList();
        Assert.assertFalse(list.isEmpty());

        list.forEach(obj -> System.out.println(obj));
    }

    @Test
    public void applyNativeFunction() {
        String jpql = "select o from Order o where function('invoice_above_average', o.total) = 1";
        String jpqlString = "select function('dayname', o.creationDate) from Order o " +
                "where function('invoice_above_average', o.total) = 1";

        TypedQuery<Order> typedQuery = entityManager.createQuery(jpql, Order.class);
        TypedQuery<String> stringTypedQuery = entityManager.createQuery(jpqlString, String.class);

        List<Order> list = typedQuery.getResultList();
        List<String> stringList = stringTypedQuery.getResultList();
        Assert.assertFalse(list.isEmpty());

        list.forEach(obj -> System.out.println(obj));
        stringList.forEach(obj -> System.out.println(obj));
    }

    @Test
    public void applyCollectionFunction() {
        String jpql = "select size (o.orderItems) from Order o where size(o.orderItems) > 1";

        TypedQuery<Object[]> typedQuery = entityManager.createQuery(jpql, Object[].class);

        List<Object[]> list = typedQuery.getResultList();
        Assert.assertFalse(list.isEmpty());

        list.forEach(size -> System.out.println(size));
    }

    @Test
    public void applyNumberFunction() {
        String jpql = "select abs(o.total), mod(o.id, 2), sqrt(o.total) o from Order o " +
                "where abs(o.total) > 1000"; // Abs = take the absolute number

        TypedQuery<Object[]> typedQuery = entityManager.createQuery(jpql, Object[].class);

        List<Object[]> list = typedQuery.getResultList();
        Assert.assertFalse(list.isEmpty());

        list.forEach(arr -> System.out.println(arr[0] + " | " + arr[1] + " | " + arr[2]));
    }

    @Test
    public void applyDateFunction() {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
        // current_date, current_time, current_timestamp

        String jpql = "select year(o.creationDate), month(o.creationDate), day(o.creationDate), " +
                "hour(o.creationDate), minute(o.creationDate), second(o.creationDate) from Order o ";
//                + "where o.creationDate < current_date";

        TypedQuery<Object[]> typedQuery = entityManager.createQuery(jpql, Object[].class);

        List<Object[]> list = typedQuery.getResultList();
        Assert.assertFalse(list.isEmpty());

        list.forEach(date -> System.out.println(date[0] + " | " + date[1] + " | " + date[2]));
    }

    @Test
    public void applyStringFunction() {
        String jpql = "select c.name, concat('Category: ', c.name) from Category c"; // Concat
//        String jpql = "select c.name, length(c.name) from Category c"; // Length
//        String jpql = "select c.name, locate('a', c.name) from Category c"; // Locate
//        String jpql = "select c.name, substring(c.name, 1, 2) from Category c"; // Substring
//        String jpql = "select c.name, trim(c.name) from Category c"; // Trim
//        String jpql = "select c.name, lower(c.name) from Category c"; // Lower / Upper

        TypedQuery<Object[]> typedQuery = entityManager.createQuery(jpql, Object[].class);

        List<Object[]> list = typedQuery.getResultList();
        Assert.assertFalse(list.isEmpty());

        list.forEach(category -> System.out.println(category[0] + ", " + category[1]));
    }
}
