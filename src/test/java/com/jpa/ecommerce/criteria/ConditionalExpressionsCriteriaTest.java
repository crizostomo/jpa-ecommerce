package com.jpa.ecommerce.criteria;

import com.jpa.ecommerce.EntityManagerTest;
import com.jpa.ecommerce.model.*;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class ConditionalExpressionsCriteriaTest extends EntityManagerTest {

    @Test
    public void orderingResults() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Client> criteriaQuery = criteriaBuilder.createQuery(Client.class);
        Root<Client> root = criteriaQuery.from(Client.class);

        criteriaQuery.orderBy(criteriaBuilder.asc(root.get(Client_.name)));

        TypedQuery<Client> typedQuery = entityManager.createQuery(criteriaQuery);
        List<Client> orderList = typedQuery.getResultList();
        Assert.assertFalse(orderList.isEmpty());

        orderList.forEach(c -> System.out.println("ID: " + c.getId() + ", Name: " + c.getName()));
    }

    @Test
    public void usingLogicalOperators() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Order> criteriaQuery = criteriaBuilder.createQuery(Order.class);
        Root<Order> root = criteriaQuery.from(Order.class);

        criteriaQuery.select(root);

        // select o from Order o where (total > 500 and status = 'WAITING' or 'PAID') and creationDate > ?
        criteriaQuery.where(criteriaBuilder.or(
                        criteriaBuilder.equal(root.get(Order_.status), OrderStatus.WAITING),
                criteriaBuilder.equal(root.get(Order_.status), OrderStatus.PAID)
                ),
                criteriaBuilder.greaterThan(root.get(Order_.creationDate), LocalDateTime.now().minusDays(5)),
                criteriaBuilder.greaterThan(root.get(Order_.total), new BigDecimal(500))
        );

        TypedQuery<Order> typedQuery = entityManager.createQuery(criteriaQuery);
        List<Order> orderList = typedQuery.getResultList();
        Assert.assertFalse(orderList.isEmpty());

        orderList.forEach(p -> System.out.println("ID: " + p.getId() + ", Total: " + p.getTotal() + ", Status: " + p.getStatus()));
    }

    @Test // Similar to the exercise in the Class ConditionalExpressionsTest - usingDifferent
    public void usingDifferent() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Order> criteriaQuery = criteriaBuilder.createQuery(Order.class);
        Root<Order> root = criteriaQuery.from(Order.class);

        criteriaQuery.select(root);

        criteriaQuery.where(criteriaBuilder.notEqual(root.get(Order_.total), new BigDecimal(500)));

        TypedQuery<Order> typedQuery = entityManager.createQuery(criteriaQuery);
        List<Order> orderList = typedQuery.getResultList();
        Assert.assertFalse(orderList.isEmpty());

        orderList.forEach(p -> System.out.println("ID: " + p.getId() + ", Total: " + p.getTotal()));
    }

    @Test // Similar to the exercise in the Class ConditionalExpressionsTest - usingBetween
    public void usingBetween() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Order> criteriaQuery = criteriaBuilder.createQuery(Order.class);
        Root<Order> root = criteriaQuery.from(Order.class);

        criteriaQuery.select(root);

//        criteriaQuery.where(criteriaBuilder.between(root.get(Order_.total), new BigDecimal(500), new BigDecimal(2400)));
        criteriaQuery.where(criteriaBuilder.between(root.get(Order_.creationDate), LocalDateTime.now().minusDays(5), LocalDateTime.now()));

        TypedQuery<Order> typedQuery = entityManager.createQuery(criteriaQuery);
        List<Order> orderList = typedQuery.getResultList();
        Assert.assertFalse(orderList.isEmpty());

        orderList.forEach(p -> System.out.println("ID: " + p.getId() + ", Total: " + p.getTotal()));
    }

    @Test // Similar to the exercise in the Class ConditionalExpressionsTest - greaterLesserWithDaTES
    public void greaterLesserWithDates() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Order> criteriaQuery = criteriaBuilder.createQuery(Order.class);
        Root<Order> root = criteriaQuery.from(Order.class);

        criteriaQuery.select(root);

        criteriaQuery.where(criteriaBuilder.greaterThanOrEqualTo(root.get(Order_.creationDate), LocalDateTime.now().minusDays(3)));

        TypedQuery<Order> typedQuery = entityManager.createQuery(criteriaQuery);
        List<Order> orderList = typedQuery.getResultList();
        Assert.assertFalse(orderList.isEmpty());
    }

    @Test // Similar to the exercise in the Class ConditionalExpressionsTest - greaterLesser
    public void greaterLesser() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Product> criteriaQuery = criteriaBuilder.createQuery(Product.class);
        Root<Product> root = criteriaQuery.from(Product.class);

        criteriaQuery.select(root);

        criteriaQuery.where(criteriaBuilder.greaterThan(root.get(Product_.price), new BigDecimal(800)),
                criteriaBuilder.lessThan(root.get(Product_.price), new BigDecimal(3500)));
//        criteriaQuery.where(criteriaBuilder.greaterThan(root.get("price"), 799)); // Another option if the type is not important

        TypedQuery<Product> typedQuery = entityManager.createQuery(criteriaQuery);
        List<Product> productList = typedQuery.getResultList();
        Assert.assertFalse(productList.isEmpty());

        productList.forEach(p -> System.out.println("ID: " + p.getId() + ", Name: " + p.getName() + ", Price: " + p.getPrice()));
    }

    @Test // Similar to the exercise in the Class ConditionalExpressionsTest - isEmpty
    public void isEmpty() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Product> criteriaQuery = criteriaBuilder.createQuery(Product.class);
        Root<Product> root = criteriaQuery.from(Product.class);

        criteriaQuery.select(root);

//        criteriaQuery.where(root.get(Product_.categories).isEmpty()); // Option 1
        criteriaQuery.where(criteriaBuilder.isEmpty(root.get(Product_.categories))); // Option 2 | You could use isNotEmpty too

        TypedQuery<Product> typedQuery = entityManager.createQuery(criteriaQuery);
        List<Product> productList = typedQuery.getResultList();
        Assert.assertTrue(productList.isEmpty());
    }

    @Test // Similar to the exercise in the Class ConditionalExpressionsTest - isNull
    public void isNull() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Product> criteriaQuery = criteriaBuilder.createQuery(Product.class);
        Root<Product> root = criteriaQuery.from(Product.class);

        criteriaQuery.select(root);

//        criteriaQuery.where(root.get(Product_.photo).isNull()); // Option 1
        criteriaQuery.where(criteriaBuilder.isNull(root.get(Product_.photo))); // Option 2 | You could use isNotNull too

        TypedQuery<Product> typedQuery = entityManager.createQuery(criteriaQuery);
        List<Product> productList = typedQuery.getResultList();
        Assert.assertFalse(productList.isEmpty());
    }

    @Test  // Similar to the exercise in the Class ConditionalExpressionsTest - useConditionalExpressionLike
    public void conditionalExpressionLike() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Client> criteriaQuery = criteriaBuilder.createQuery(Client.class);
        Root<Client> root = criteriaQuery.from(Client.class);

        criteriaQuery.select(root);

        criteriaQuery.where(criteriaBuilder.like(root.get(Client_.name), "%o%")); //A% - It starts with 'A' | %A - It finishes with 'A' | %A% - It contains 'A'

        TypedQuery<Client> typedQuery = entityManager.createQuery(criteriaQuery);

        List<Client> clientList = typedQuery.getResultList();
        Assert.assertFalse(clientList.isEmpty());
    }
}
