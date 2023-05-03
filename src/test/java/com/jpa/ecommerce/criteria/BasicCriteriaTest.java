package com.jpa.ecommerce.criteria;

import com.jpa.ecommerce.EntityManagerTest;
import com.jpa.ecommerce.model.Client;
import com.jpa.ecommerce.model.Order;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.List;

public class BasicCriteriaTest extends EntityManagerTest {

    @Test
    public void selectAnAttributeBigDecimal() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<BigDecimal> criteriaQuery = criteriaBuilder.createQuery(BigDecimal.class);
        Root<Order> root = criteriaQuery.from(Order.class);

        criteriaQuery.select(root.get("total"));

        criteriaQuery.where(criteriaBuilder.equal(root.get("id"), 1));

        TypedQuery<BigDecimal> typedQuery = entityManager.createQuery(criteriaQuery);

        BigDecimal total = typedQuery.getSingleResult();
        Assert.assertEquals(new BigDecimal("2100.00"), total);

        List<BigDecimal> list = typedQuery.getResultList();
        Assert.assertFalse(list.isEmpty());
    }

    @Test
    public void selectAnAttributeClient() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Client> criteriaQuery = criteriaBuilder.createQuery(Client.class);
        Root<Order> root = criteriaQuery.from(Order.class);

        criteriaQuery.select(root.get("client"));

        criteriaQuery.where(criteriaBuilder.equal(root.get("id"), 1));

        TypedQuery<Client> typedQuery = entityManager.createQuery(criteriaQuery);

        Client client = typedQuery.getSingleResult();
        Assert.assertEquals("Roronoa Zoro", client.getName());

        List<Client> list = typedQuery.getResultList();
        Assert.assertFalse(list.isEmpty());
    }

    @Test
    public void searchByIdentifier() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Order> criteriaQuery = criteriaBuilder.createQuery(Order.class);
        Root<Order> root = criteriaQuery.from(Order.class);

        criteriaQuery.select(root);

        criteriaQuery.where(criteriaBuilder.equal(root.get("id"), 1));

        TypedQuery<Order> typedQuery = entityManager.createQuery(criteriaQuery);

        Order order = typedQuery.getSingleResult();
        Assert.assertNotNull(order);

        List<Order> list = typedQuery.getResultList();
        Assert.assertFalse(list.isEmpty());
    }
}