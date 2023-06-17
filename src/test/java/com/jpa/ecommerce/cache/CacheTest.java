package com.jpa.ecommerce.cache;

import com.jpa.ecommerce.model.Order;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class CacheTest {

    protected static EntityManagerFactory entityManagerFactory;

    @BeforeClass
    public static void setUpBeforeClass() {
        entityManagerFactory = Persistence
                .createEntityManagerFactory("Ecommerce-PU");
    }

    @AfterClass
    public static void tearDownAfterClass() {
        entityManagerFactory.close();
    }

    @Test
    public void searchFromCache() {
        EntityManager entityManager1 = entityManagerFactory.createEntityManager();
        EntityManager entityManager2 = entityManagerFactory.createEntityManager();

        System.out.println("Searching from instance 1: ");
        entityManager1.find(Order.class, 1);

        System.out.println("Searching from instance 2: ");
        entityManager2.find(Order.class, 2);
    }

    @Test
    public void addOrdersInTheCache() {
        EntityManager entityManager1 = entityManagerFactory.createEntityManager();
        EntityManager entityManager2 = entityManagerFactory.createEntityManager();

        System.out.println("Searching from instance 1: ");
        entityManager1.createQuery("select o from Order o", Order.class)
                .getResultList();

        System.out.println("Searching from instance 2: ");
        entityManager2.find(Order.class, 2);
    }
}
