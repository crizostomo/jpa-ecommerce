package com.jpa.ecommerce.jpql;

import com.jpa.ecommerce.EntityManagerTest;
import com.jpa.ecommerce.model.OrderStatus;
import jakarta.persistence.TypedQuery;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class GroupByTest extends EntityManagerTest {

    @Test
    public void limitGroupWithHaving() {
//         Total amount of sales among the categories that sell the most
        String jpql = "select cat.name, sum(oi.productPrice) from OrderItem oi " +
                "join oi.product pro join pro.categories cat " +
                "group by cat.id " +
                "having sum(oi.productPrice) > 1500"; // avg, max, min, count

        TypedQuery<Object[]> typedQuery = entityManager.createQuery(jpql, Object[].class);

        List<Object[]> list = typedQuery.getResultList();
        Assert.assertFalse(list.isEmpty());

        list.forEach(arr -> System.out.println(arr[0] + ", " + arr[1]));
    }

    @Test
    public void groupAndFilterResult() {
//         Total amount of sales per month
//        String jpql = "select concat(year(o.creationDate), '/', function('monthname', o.creationDate)), sum(o.total) " +
//                "from Order o " +
//                "where year(o.creationDate) = year(current_date) " +
//                "and o.status = :status " +
//                "group by year(o.creationDate), month(o.creationDate), o.creationDate";

//         Total amount of sales per category
//        String jpql = "select c.name, sum(oi.productPrice) from OrderItem oi " +
//                "join oi.product pro join pro.categories c join oi.order o " +
//                "where year(o.creationDate) = year(current_date) and month(o.creationDate) = month(current_date) " +
//                "group by c.id";

//                Total amount of sales by client
        String jpql = "select c.name, sum(oi.productPrice) from OrderItem oi " +
                "join oi.order o join o.client c join oi.order o2 " +
                "where year(o.creationDate) = year(current_date) and month(o.creationDate) >= (month(current_date) - 3) " +
                "group by c.id";

        TypedQuery<Object[]> typedQuery = entityManager.createQuery(jpql, Object[].class);
//        typedQuery.setParameter("status", OrderStatus.PAID);

        List<Object[]> list = typedQuery.getResultList();
        Assert.assertFalse(list.isEmpty());

        list.forEach(arr -> System.out.println(arr[0] + ", " + arr[1]));
    }

    @Test
    public void groupResult() {
//         Quantity of products by category
//        String jpql = "select c.name, count(p.id) from Category c join c.products p group by c.id";

//         Total amount of sales per month
//        String jpql = "select concat(year(o.creationDate), ' ', function('monthname', o.creationDate)), sum(o.total) from Order o " +
//                "group by year(o.creationDate), month(o.creationDate), o.creationDate";

//         Total amount of sales per category
//        String jpql = "select c.name, sum(oi.productPrice) from OrderItem oi join oi.product pro join pro.categories c " +
//                "group by c.id";

//                Total amount of sales by client
//        String jpql = "select c.name, sum(oi.productPrice) from OrderItem oi " +
//                " join oi.order o join o.client c group by c.id";

//        Total amount of sales per day and by category
        String jpql = "select concat(year(o.creationDate), '/', month(o.creationDate), '/', day(o.creationDate), " +
                "concat(c.name, ': ', sum(oi.productPrice))) " +
                "from OrderItem oi " +
                "join oi.order o " +
                "join oi.product pro " +
                "join pro.categories c " +
                "group by year(o.creationDate), month(o.creationDate), day(o.creationDate), c.id, c.name, o.creationDate " +
                "order by o.creationDate, c.name ";

        TypedQuery<Object[]> typedQuery = entityManager.createQuery(jpql, Object[].class);

        List<Object[]> list = typedQuery.getResultList();
        Assert.assertFalse(list.isEmpty());

        list.forEach(arr -> System.out.println(arr[0]));
    }
}
