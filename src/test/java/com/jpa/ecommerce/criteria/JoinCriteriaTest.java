package com.jpa.ecommerce.criteria;

import com.jpa.ecommerce.EntityManagerTest;
import com.jpa.ecommerce.model.*;
import com.jpa.ecommerce.model.Order;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class JoinCriteriaTest extends EntityManagerTest {

    @Test
    public void usingJoinFetchCast() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Client> criteriaQuery = criteriaBuilder.createQuery(Client.class);
        Root<Order> root = criteriaQuery.from(Order.class);
//        Join<Order, Client> clientJoin = (Join<Order, Client>) root.<Order, Client>join("client");
        Join<Order, Client> clientJoin = root.join("client");

        criteriaQuery.select(clientJoin);

        criteriaQuery.where(criteriaBuilder.equal(clientJoin.get("id"), 1));

        TypedQuery<Client> typedQuery = entityManager.createQuery(criteriaQuery);

        Client client = typedQuery.getSingleResult();
        Assert.assertNotNull(client);
        Assert.assertTrue(client.getId().equals(1));
    }

    @Test
    public void usingJoinFetch() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Order> criteriaQuery = criteriaBuilder.createQuery(Order.class);
        Root<Order> root = criteriaQuery.from(Order.class);
        root.fetch("orderItems");
        root.fetch("invoice", JoinType.LEFT); // To retrieve all orders that have or not connection with invoices

        criteriaQuery.select(root);

        criteriaQuery.where(criteriaBuilder.equal(root.get("id"), 1));

        TypedQuery<Order> typedQuery = entityManager.createQuery(criteriaQuery);

        Order order = typedQuery.getSingleResult();
        Assert.assertNotNull(order);
        Assert.assertFalse(order.getOrderItems().isEmpty());
    }

    @Test
    public void usingLeftOuterJoin() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Order> criteriaQuery = criteriaBuilder.createQuery(Order.class);
        Root<Order> root = criteriaQuery.from(Order.class);
        Join<Order, Payment> joinPayment = root.join("payment", JoinType.LEFT);
        // We have 5 Orders and 4 Payments linked to them, but since it is LEFT Join it will bring all th orders

        criteriaQuery.select(root);

        TypedQuery<Order> typedQuery = entityManager.createQuery(criteriaQuery);
        List<Order> list = typedQuery.getResultList();
        Assert.assertTrue(list.size() == 4);
    }

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
