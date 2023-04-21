package com.jpa.ecommerce.jpql;

import com.jpa.ecommerce.EntityManagerTest;
import jakarta.persistence.TypedQuery;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class PathExpressionTest extends EntityManagerTest {

    @Test
    public void usePathExpressions() {
        String jpql = "select o.client.name from Order o";

        TypedQuery<Object[]> typedQuery = entityManager.createQuery(jpql, Object[].class);

        List<Object[]> list = typedQuery.getResultList();
        Assert.assertFalse(list.isEmpty());
    }
}
