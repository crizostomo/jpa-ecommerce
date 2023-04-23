package com.jpa.ecommerce.jpql;

import com.jpa.ecommerce.EntityManagerTest;
import com.jpa.ecommerce.model.Category;
import jakarta.persistence.TypedQuery;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class StringsFunctionTest extends EntityManagerTest {

    @Test
    public void applyFunction() {
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
