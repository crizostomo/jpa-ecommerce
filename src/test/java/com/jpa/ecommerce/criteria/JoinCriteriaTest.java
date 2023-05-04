package com.jpa.ecommerce.criteria;

import com.jpa.ecommerce.EntityManagerTest;
import com.jpa.ecommerce.model.*;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Root;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class JoinCriteriaTest extends EntityManagerTest {

    @Test
    public void usingOnInJoin() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Order> criteriaQuery = criteriaBuilder.createQuery(Order.class);
        Root<Order> root = criteriaQuery.from(Order.class);
        Join<Order, Payment> joinPayment = root.join("payment");
        joinPayment.on(criteriaBuilder.equal(joinPayment.get("status"), PaymentStatus.PROCESSING));

        criteriaQuery.select(root);

        TypedQuery<Order> typedQuery = entityManager.createQuery(criteriaQuery);
        List<Order> list = typedQuery.getResultList();
        Assert.assertTrue(list.size() == 2);
    }

    @Test
    public void usingJoinInOrder() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Order> criteriaQuery = criteriaBuilder.createQuery(Order.class);
        Root<Order> root = criteriaQuery.from(Order.class);
        Join<Order, Payment> joinPayment = root.join("payment"); // Equivalent to "select o from Order o join o.payment pay"
//        Join<Order, OrderItem> joinItems = root.join("orderItems");
//        Join<OrderItem, Product> joinItemProduct = joinItems.join("product");

        criteriaQuery.select(root);

        TypedQuery<Order> typedQuery = entityManager.createQuery(criteriaQuery);
        List<Order> list = typedQuery.getResultList();
        Assert.assertTrue(list.size() == 4);
    }

    @Test
    public void usingJoinInPayment() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Payment> criteriaQuery = criteriaBuilder.createQuery(Payment.class);
        Root<Order> root = criteriaQuery.from(Order.class);
        Join<Order, Payment> joinPayment = root.join("payment"); // Equivalent to "select o from Order o join o.payment pay"

        criteriaQuery.select(joinPayment);
        criteriaQuery.where(criteriaBuilder.equal(joinPayment.get("status"), PaymentStatus.PROCESSING));

        TypedQuery<Payment> typedQuery = entityManager.createQuery(criteriaQuery);
        List<Payment> list = typedQuery.getResultList();
        Assert.assertTrue(list.size() == 2);
    }
}
