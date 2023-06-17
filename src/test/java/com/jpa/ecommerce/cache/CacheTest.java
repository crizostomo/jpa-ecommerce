package com.jpa.ecommerce.cache;

import com.jpa.ecommerce.model.Order;
import jakarta.persistence.Cache;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.AfterClass;
import org.junit.Assert;
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

    @Test
    public void removeFromCache() {
        Cache cache = entityManagerFactory.getCache();
        EntityManager entityManager1 = entityManagerFactory.createEntityManager();
        EntityManager entityManager2 = entityManagerFactory.createEntityManager();

        System.out.println("Searching from instance 1: ");
        entityManager1.createQuery("select o from Order o", Order.class)
                .getResultList();

        System.out.println("Removing order 1 from cache ");
//        cache.evict(Order.class, 1); // To remove just order of id 1
//        cache.evict(Order.class);
        cache.evictAll(); // To remove everything in the cache

        System.out.println("Searching from instance 2: ");
        entityManager2.find(Order.class, 1);
        entityManager2.find(Order.class, 2);
    }

    @Test
    public void verifyIfSmtIsInCache() {
        Cache cache = entityManagerFactory.getCache();
        EntityManager entityManager1 = entityManagerFactory.createEntityManager();

        System.out.println("Searching from instance 1: ");
        entityManager1.createQuery("select o from Order o", Order.class)
                .getResultList();

        Assert.assertTrue(cache.contains(Order.class, 1));
        Assert.assertTrue(cache.contains(Order.class, 2));
    }
}
