package com.jpa.ecommerce;

import jakarta.persistence.EntityManager;
import org.junit.After;
import org.junit.Before;

public class EntityManagerTest extends EntityManagerFactoryTest{

    protected EntityManager entityManager;

        @Before
    public void setUp() {
        entityManager = entityManagerFactory.createEntityManager();
    }

    @After
    public void tearDown() {
        entityManager.close();
    }
}