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
