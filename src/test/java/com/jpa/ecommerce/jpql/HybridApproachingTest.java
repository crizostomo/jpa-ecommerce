package com.jpa.ecommerce.jpql;

import com.jpa.ecommerce.EntityManagerTest;
import com.jpa.ecommerce.model.Category;
import com.jpa.ecommerce.model.Product;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

public class HybridApproachingTest extends EntityManagerTest {

    @BeforeClass
    public static void setUpBeforeClass() {
        entityManagerFactory = Persistence
                .createEntityManagerFactory("Ecommerce-PU");

        EntityManager em = entityManagerFactory.createEntityManager();

        String jpql = "select c from Category c";
        TypedQuery<Category> typedQuery = em.createQuery(jpql, Category.class);

        entityManagerFactory.addNamedQuery("Category.list", typedQuery);
    }

    @Test
    public void executeSearchBySpecificProductXMLFile() {
        TypedQuery<Product> typedQueryList = entityManager.createNamedQuery("Category.list", Product.class);

        List<Product> list = typedQueryList.getResultList();

        Assert.assertFalse(list.isEmpty());
    }
}
