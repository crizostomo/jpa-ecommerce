package com.jpa.ecommerce.startingwithjpa;

import com.jpa.ecommerce.EntityManagerTest;
import com.jpa.ecommerce.model.Product;
import org.junit.*;

public class ConsultingRegistersTest extends EntityManagerTest {

    @Test
    public void searchByIdentifier() {
        Product product = entityManager.find(Product.class, 1);
//        Product product = entityManager.getReference(Product.class, 1);

        Assert.assertNotNull(product);
        Assert.assertEquals("Kindle", product.getName());
    }

    @Test
    public void updateTheReference() {
        Product product = entityManager.find(Product.class, 1);
        product.setName("Microfone Samson");

        entityManager.refresh(product);

        Assert.assertEquals("Kindle", product.getName());
    }
}