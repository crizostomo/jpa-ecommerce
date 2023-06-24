package com.jpa.ecommerce.multitenant;

import com.jpa.ecommerce.EntityManagerTest;
import com.jpa.ecommerce.hibernate.EcmCurrentTenantIdentifierResolver;
import com.jpa.ecommerce.model.Product;
import jakarta.persistence.EntityManager;
import org.junit.Assert;
import org.junit.Test;

public class MultiTenantTest extends EntityManagerTest {

    @Test
    public void machineApproach() {
        EcmCurrentTenantIdentifierResolver.setTenantIdentifier("jpa_ecommerce");
        EntityManager entityManager1 = entityManagerFactory.createEntityManager();
        Product product1 = entityManager1.find(Product.class, 1);
        Assert.assertEquals("Kindle", product1.getName());
        entityManager1.close();

        EcmCurrentTenantIdentifierResolver.setTenantIdentifier("store_ecommerce");
        EntityManager entityManager2 = entityManagerFactory.createEntityManager();
        Product product2 = entityManager2.find(Product.class, 1);
        Assert.assertEquals("Kindle Paperwhite", product2.getName());
        entityManager2.close();
    }

    @Test
    public void schemaApproach() {
        EcmCurrentTenantIdentifierResolver.setTenantIdentifier("jpa_ecommerce");
        EntityManager entityManager1 = entityManagerFactory.createEntityManager();
        Product product1 = entityManager1.find(Product.class, 1);
        Assert.assertEquals("Kindle", product1.getName());
        entityManager1.close();

        EcmCurrentTenantIdentifierResolver.setTenantIdentifier("store_ecommerce");
        EntityManager entityManager2 = entityManagerFactory.createEntityManager();
        Product product2 = entityManager2.find(Product.class, 1);
        Assert.assertEquals("Kindle Paperwhite", product2.getName());
        entityManager2.close();
    }
}
