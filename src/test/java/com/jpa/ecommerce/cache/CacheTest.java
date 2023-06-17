package com.jpa.ecommerce.cache;

import com.jpa.ecommerce.model.Order;
import jakarta.persistence.*;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

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

    @Test
    public void analyseCacheOptions() {
        Cache cache = entityManagerFactory.getCache();
        EntityManager entityManager1 = entityManagerFactory.createEntityManager();

        System.out.println("Searching from instance 1: ");
        entityManager1.createQuery("select o from Order o", Order.class)
                .getResultList();

        Assert.assertTrue(cache.contains(Order.class, 1));
    }

    @Test
    public void controllingCacheDynamically() {
        Cache cache = entityManagerFactory.getCache();

        System.out.println("Searching all orders....................... ");
        EntityManager entityManager1 = entityManagerFactory.createEntityManager();
//        entityManager1.setProperty("jakarta.persistence.cache.storeMode", CacheStoreMode.BYPASS);
        entityManager1.createQuery("select o from Order o", Order.class)
                .setHint("jakarta.persistence.cache.storeMode", CacheStoreMode.BYPASS) // BYPASS - Bring the retrieved value and do not input in the cache
                .getResultList();

        System.out.println("Searching order with ID = 2....................... ");
        EntityManager entityManager2 = entityManagerFactory.createEntityManager();
        Map<String, Object> properties = new HashMap<>();
//        properties.put("jakarta.persistence.cache.storeMode", CacheStoreMode.BYPASS);
//        properties.put("jakarta.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        entityManager2.find(Order.class, 2, properties);

        System.out.println("Searching all orders AGAIN....................... ");
        EntityManager entityManager3 = entityManagerFactory.createEntityManager();
        entityManager3.createQuery("select o from Order o", Order.class)
                .setHint("jakarta.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS)
                .getResultList();
    }

    private static void wait (int seconds) {
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {

        }
    }

    private static void log(Object obj) {
        System.out.println("([LOG " + System.currentTimeMillis() + "]" + obj);
    }

    @Test
    public void isCache() {
        Cache cache = entityManagerFactory.getCache();
        EntityManager entityManager1 = entityManagerFactory.createEntityManager();
        EntityManager entityManager2 = entityManagerFactory.createEntityManager();

        System.out.println("Searching and including in the cache....... ");
        entityManager1.createQuery("select o from Order o", Order.class)
                .getResultList();

        log("-----");

        wait(1);
        Assert.assertTrue(cache.contains(Order.class, 2));
        entityManager2.find(Order.class, 2);

        wait(3);
        Assert.assertFalse(cache.contains(Order.class, 2));
    }
}
