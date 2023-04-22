package com.jpa.ecommerce.jpql;

import com.jpa.ecommerce.EntityManagerTest;
import com.jpa.ecommerce.model.Order;
import com.jpa.ecommerce.model.Product;
import jakarta.persistence.TypedQuery;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class ConditionalExpressionsTest extends EntityManagerTest {

    @Test
    public void usingDifferent() {
        String jpql = "select p from Product p where p.price <> :price";

        TypedQuery<Order> typedQuery = entityManager.createQuery(jpql, Order.class);
        typedQuery.setParameter("price", 100);

        List<Order> list = typedQuery.getResultList();
        Assert.assertFalse(list.isEmpty());
    }

    @Test
    public void useOfBetween() {
//        String jpql = "select p from Product p where p.price between :initialPrice and :finalPrice";
        String jpql = "select o from Order o where o.creationDate between :initialDate and :finalDate";

//        TypedQuery<Product> typedQuery = entityManager.createQuery(jpql, Product.class);
//        typedQuery.setParameter("initialPrice", new BigDecimal(400));
//        typedQuery.setParameter("finalPrice", new BigDecimal(1500));
        TypedQuery<Order> typedQuery = entityManager.createQuery(jpql, Order.class);
        typedQuery.setParameter("initialDate", LocalDateTime.now().minusDays(2));
        typedQuery.setParameter("finalDate", LocalDateTime.now());

//        List<Product> list = typedQuery.getResultList();
        List<Order> list = typedQuery.getResultList();
        Assert.assertFalse(list.isEmpty());
    }

    @Test
    public void greaterLesserWithDates() {
        String jpql = "select o from Order o where o.creationDate > :date";

        TypedQuery<Order> typedQuery = entityManager.createQuery(jpql, Order.class);
        typedQuery.setParameter("date", LocalDateTime.now().minusDays(2));

        List<Order> list = typedQuery.getResultList();
        Assert.assertFalse(list.isEmpty());
    }

    @Test
    public void greaterLesser() {
//        String jpql = "select p from Product p where p.price > :price";
        String jpql = "select p from Product p where p.price > :initialPrice and p.price <= :finalPrice";

        TypedQuery<Product> typedQuery = entityManager.createQuery(jpql, Product.class);
//        typedQuery.setParameter("price", new BigDecimal(500));
        typedQuery.setParameter("initialPrice", new BigDecimal(400));
        typedQuery.setParameter("finalPrice", new BigDecimal(1500));

        List<Product> list = typedQuery.getResultList();
        Assert.assertFalse(list.isEmpty());
    }

    @Test
    public void isNull() {
        String jpql = "select p from Product p where p.photo is null";
//        String jpql = "select p from Product p where p.photo is not null";

        TypedQuery<Object[]> typedQuery = entityManager.createQuery(jpql, Object[].class);

        List<Object[]> list = typedQuery.getResultList();
        Assert.assertFalse(list.isEmpty());
    }

    @Test
    public void isEmpty() {
        String jpql = "select p from Product p where p.categories is empty";

        TypedQuery<Object[]> typedQuery = entityManager.createQuery(jpql, Object[].class);

        List<Object[]> list = typedQuery.getResultList();
        Assert.assertFalse(list.isEmpty());
    }

    @Test
    public void useConditionalExpressionLike() {
        String jpql = "select c from Client c where c.name like concat(:name, '%')";
//        String jpql = "select c from Client c where c.name like concat('%', :name, '%')"; // You want to search the middle
//        String jpql = "select c from Client c where c.name like concat('%', :name)"; // If you want what it is at the end

        TypedQuery<Object[]> typedQuery = entityManager.createQuery(jpql, Object[].class);
        typedQuery.setParameter("name", "Roronoa");

        List<Object[]> list = typedQuery.getResultList();
        Assert.assertFalse(list.isEmpty());
    }
}
