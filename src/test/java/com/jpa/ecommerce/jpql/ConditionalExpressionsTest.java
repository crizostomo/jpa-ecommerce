package com.jpa.ecommerce.jpql;

import com.jpa.ecommerce.EntityManagerTest;
import jakarta.persistence.TypedQuery;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class ConditionalExpressionsTest extends EntityManagerTest {

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
