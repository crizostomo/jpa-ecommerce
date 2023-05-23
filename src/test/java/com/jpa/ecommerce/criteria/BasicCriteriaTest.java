package com.jpa.ecommerce.criteria;

import com.jpa.ecommerce.EntityManagerTest;
import com.jpa.ecommerce.dto.ProductDTO;
import com.jpa.ecommerce.model.*;
import jakarta.persistence.Tuple;
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
    public void usingDistinct() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Order> criteriaQuery = criteriaBuilder.createQuery(Order.class);
        Root<Order> root = criteriaQuery.from(Order.class);
        root.join(Order_.orderItems);

        criteriaQuery.select(root).distinct(true);
//        criteriaQuery.distinct(true);

        TypedQuery<Order> typedQuery = entityManager.createQuery(criteriaQuery);
        List<Order> list = typedQuery.getResultList();

        list.forEach(p -> System.out.println("ID: " + p.getId()));
    }

    @Test
    public void orderingResults() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Client> criteriaQuery = criteriaBuilder.createQuery(Client.class);
        Root<Client> root = criteriaQuery.from(Client.class);

        criteriaQuery.orderBy(criteriaBuilder.desc(root.get(Client_.name)));

        TypedQuery<Client> typedQuery = entityManager.createQuery(criteriaQuery);

        List<Client> list = typedQuery.getResultList();
        Assert.assertFalse(list.isEmpty());

        list.forEach(c -> System.out.println(c.getId() + ", " + c.getName()));
    }

    @Test
    public void projectTheDTOResult() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<ProductDTO> criteriaQuery = criteriaBuilder.createQuery(ProductDTO.class);
        Root<Product> root = criteriaQuery.from(Product.class);

        criteriaQuery.select(criteriaBuilder.construct(ProductDTO.class, root.get("id"), root.get("name")));

        TypedQuery<ProductDTO> typedQuery = entityManager.createQuery(criteriaQuery);
        List<ProductDTO> list = typedQuery.getResultList();
        Assert.assertFalse(list.isEmpty());

        list.forEach(dto -> System.out.println("ID: " + dto.getId() + ", " + "NAME: " + dto.getName()));
    }

    @Test
    public void projectTheTupleResult() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Tuple> criteriaQuery = criteriaBuilder.createTupleQuery();
        Root<Product> root = criteriaQuery.from(Product.class);

//        criteriaQuery.select(criteriaBuilder.tuple(root.get("id"), root.get("name")));
        criteriaQuery.select(criteriaBuilder.tuple(root.get("id").alias("id"), root.get("name").alias("name")));

        TypedQuery<Tuple> typedQuery = entityManager.createQuery(criteriaQuery);
        List<Tuple> list = typedQuery.getResultList();
        Assert.assertFalse(list.isEmpty());

//        list.forEach(t -> System.out.println("ID: " + t.get(0) + ", " + "NAME: " + t.get(1)));
        list.forEach(t -> System.out.println("ID: " + t.get("id") + ", " + "NAME: " + t.get("name")));
    }

    @Test
    public void projectTheResult() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Object[]> criteriaQuery = criteriaBuilder.createQuery(Object[].class);
        Root<Product> root = criteriaQuery.from(Product.class);

        criteriaQuery.multiselect(root.get("id"), root.get("name"));

        TypedQuery<Object[]> typedQuery = entityManager.createQuery(criteriaQuery);
        List<Object[]> list = typedQuery.getResultList();
        Assert.assertFalse(list.isEmpty());

        list.forEach(arr -> System.out.println("ID: " + arr[0] + ", " + "NAME: " + arr[1]));
    }

    @Test
    public void returnAllProductsExercise() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Product> criteriaQuery = criteriaBuilder.createQuery(Product.class);
        Root<Product> root = criteriaQuery.from(Product.class);

        criteriaQuery.select(root);

        TypedQuery<Product> typedQuery = entityManager.createQuery(criteriaQuery);
        List<Product> list = typedQuery.getResultList();
        Assert.assertFalse(list.isEmpty());
    }

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