package com.jpa.ecommerce.concurrency;

import com.jpa.ecommerce.model.Product;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.LockModeType;
import jakarta.persistence.Persistence;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

public class PessimistLockTest {

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

    private static void log(Object obj, Object... args) {
        System.out.println(
                String.format("[LOG " + System.currentTimeMillis() + "] " + obj, args)
        );
    }

    private static void wait(int seconds) {
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {}
    }

    //    @Test // To execute this test it is needed ti comment the version
    public void usingLockInTypedQuery() {
        Runnable runnable1 = () -> {
            log("Starting Runnable 01.");

            String newDescription = "Detailed description. CTM: " + System.currentTimeMillis();

            EntityManager entityManager1 = entityManagerFactory.createEntityManager();
            entityManager1.getTransaction().begin();

            log("Runnable 01 will load product 1.");

            List<Product> list = entityManager1
                    .createQuery("select p from Product p where p.id in (3,4,5)")
                    .setLockMode(LockModeType.PESSIMISTIC_READ)
                    .getResultList();

            Product product = list
                    .stream()
                    .filter(p -> p.getId().equals(1))
                    .findFirst()
                    .get();

            log("Runnable 01 will alter the product of ID equal to 1.");
            product.setDescription(newDescription);

            log("Runnable 01 will wait for 3 seconds.");
            wait(3);

            log("Runnable 01 will confirm the transaction.");
            entityManager1.getTransaction().commit();
            entityManager1.close();

            log("Finishing Runnable 01.");
        };

        Runnable runnable2 = () -> {
            log("Starting Runnable 02.");

            String newDescription = "Detailed description 2 CTM: " + System.currentTimeMillis();

            EntityManager entityManager2 = entityManagerFactory.createEntityManager();
            entityManager2.getTransaction().begin();

            log("Runnable 02 will load product 2.");
            Product product = entityManager2.find(
                    Product.class, 1, LockModeType.PESSIMISTIC_WRITE);

            log("Runnable 02 will alter the procut.");
            product.setDescription(newDescription);

            log("Runnable 02 will wait for 1 second.");
            wait(1);

            log("Runnable 02 will confirm the transaction.");
            entityManager2.getTransaction().commit();
            entityManager2.close();

            log("Finishing Runnable 02.");
        };

        Thread thread1 = new Thread(runnable1);
        Thread thread2 = new Thread(runnable2);

        thread1.start();

        wait(1);
        thread2.start();

        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        EntityManager entityManager3 = entityManagerFactory.createEntityManager();
        Product product = entityManager3.find(Product.class, 1);
        entityManager3.close();

        Assert.assertTrue(product.getDescription().startsWith("Detailed description 2"));

        log("Finishing test method.");
    }

    //    @Test // To execute this test it is needed ti comment the version
    public void mixingLockTypes() {
        Runnable runnable1 = () -> {
            log("Starting Runnable 01.");

            String newDescription = "Detailed description. CTM: " + System.currentTimeMillis();

            EntityManager entityManager1 = entityManagerFactory.createEntityManager();
            entityManager1.getTransaction().begin();

            log("Runnable 01 will load product 1.");
            Product product = entityManager1.find(
                    Product.class, 1, LockModeType.PESSIMISTIC_WRITE);
//                    Product.class, 1, LockModeType.PESSIMISTIC_READ);

            log("Runnable 01 will alter the product.");
            product.setDescription(newDescription);

            log("Runnable 01 will wait for 3 seconds.");
            wait(3);

            log("Runnable 01 will confirm the transaction.");
            entityManager1.getTransaction().commit();
            entityManager1.close();

            log("Finishing Runnable 01.");
        };

        Runnable runnable2 = () -> {
            log("Starting Runnable 02.");

            String newDescription = "Detailed description 2 CTM: " + System.currentTimeMillis();

            EntityManager entityManager2 = entityManagerFactory.createEntityManager();
            entityManager2.getTransaction().begin();

            log("Runnable 02 will load product 2.");
            Product product = entityManager2.find(
                    Product.class, 1, LockModeType.PESSIMISTIC_READ);
//                    Product.class, 1, LockModeType.PESSIMISTIC_WRITE);

            log("Runnable 02 will alter the procut.");
            product.setDescription(newDescription);

            log("Runnable 02 will wait for 1 second.");
            wait(1);

            log("Runnable 02 will confirm the transaction.");
            entityManager2.getTransaction().commit();
            entityManager2.close();

            log("Finishing Runnable 02.");
        };

        Thread thread1 = new Thread(runnable1);
        Thread thread2 = new Thread(runnable2);

        thread1.start();

        wait(1);
        thread2.start();

        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        EntityManager entityManager3 = entityManagerFactory.createEntityManager();
        Product product = entityManager3.find(Product.class, 1);
        entityManager3.close();

        Assert.assertTrue(product.getDescription().startsWith("Detailed description 2"));

        log("Finishing test method.");
    }

//    @Test // To execute this test it is needed ti comment the version
    public void usePessimistLock_LockModeTypePessimisticWrite() {
        Runnable runnable1 = () -> {
            log("Starting Runnable 01.");

            String newDescription = "Detailed description. CTM: " + System.currentTimeMillis();

            EntityManager entityManager1 = entityManagerFactory.createEntityManager();
            entityManager1.getTransaction().begin();

            log("Runnable 01 will load product 1.");
            Product product = entityManager1.find(
                    Product.class, 1, LockModeType.PESSIMISTIC_WRITE);

            log("Runnable 01 will alter the product.");
            product.setDescription(newDescription);

            log("Runnable 01 will wait for 3 seconds.");
            wait(3);

            log("Runnable 01 will confirm the transaction.");
            entityManager1.getTransaction().commit();
            entityManager1.close();

            log("Finishing Runnable 01.");
        };

        Runnable runnable2 = () -> {
            log("Starting Runnable 02.");

            String newDescription = "Detailed description 2 CTM: " + System.currentTimeMillis();

            EntityManager entityManager2 = entityManagerFactory.createEntityManager();
            entityManager2.getTransaction().begin();

            log("Runnable 02 will load product 2.");
            Product product = entityManager2.find(
                    Product.class, 1, LockModeType.PESSIMISTIC_WRITE);

            log("Runnable 02 will alter the procut.");
            product.setDescription(newDescription);

            log("Runnable 02 will wait for 1 second.");
            wait(1);

            log("Runnable 02 will confirm the transaction.");
            entityManager2.getTransaction().commit();
            entityManager2.close();

            log("Finishing Runnable 02.");
        };

        Thread thread1 = new Thread(runnable1);
        Thread thread2 = new Thread(runnable2);

        thread1.start();

        wait(1);
        thread2.start();

        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        EntityManager entityManager3 = entityManagerFactory.createEntityManager();
        Product product = entityManager3.find(Product.class, 1);
        entityManager3.close();

        Assert.assertTrue(product.getDescription().startsWith("Detailed description 2"));

        log("Finishing test method.");
    }

    @Test
    public void usePessimistLock_LockModeTypePessimisticRead() {
        Runnable runnable1 = () -> {
            log("Starting Runnable 01.");

            String newDescription = "Detailed description. CTM: " + System.currentTimeMillis();

            EntityManager entityManager1 = entityManagerFactory.createEntityManager();
            entityManager1.getTransaction().begin();

            log("Runnable 01 will load product 1.");
            Product product = entityManager1.find(
                    Product.class, 1, LockModeType.PESSIMISTIC_READ);

            log("Runnable 01 will alter the product.");
            product.setDescription(newDescription);

            log("Runnable 01 will wait for 3 seconds.");
            wait(3);

            log("Runnable 01 will confirm the transaction.");
            entityManager1.getTransaction().commit();
            entityManager1.close();

            log("Finishing Runnable 01.");
        };

        Runnable runnable2 = () -> {
            log("Starting Runnable 02.");

            String newDescription = "Detailed description 2 CTM: " + System.currentTimeMillis();

            EntityManager entityManager2 = entityManagerFactory.createEntityManager();
            entityManager2.getTransaction().begin();

            log("Runnable 02 will load product 2.");
            Product product = entityManager2.find(
                    Product.class, 1, LockModeType.PESSIMISTIC_READ);

            log("Runnable 02 will alter the procut.");
            product.setDescription(newDescription);

            log("Runnable 02 will wait for 1 second.");
            wait(1);

            log("Runnable 02 will confirm the transaction.");
            entityManager2.getTransaction().commit();
            entityManager2.close();

            log("Finishing Runnable 02.");
        };

        Thread thread1 = new Thread(runnable1);
        Thread thread2 = new Thread(runnable2);

        thread1.start();

        wait(1);
        thread2.start();

        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        EntityManager entityManager3 = entityManagerFactory.createEntityManager();
        Product product = entityManager3.find(Product.class, 1);
        entityManager3.close();

        Assert.assertTrue(product.getDescription().startsWith("Detailed description 2"));

        log("Finishing test method.");
    }
}