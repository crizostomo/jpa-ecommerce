package com.jpa.ecommerce.importantdetails;

import com.jpa.ecommerce.EntityManagerTest;
import com.jpa.ecommerce.model.Product;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDateTime;

public class ConverterTest extends EntityManagerTest {

    @Test
    public void converter() {
        Product product = new Product();
        product.setCreationDate(LocalDateTime.now());
        product.setName("Dell Laptop Charger");
        product.setActive(Boolean.TRUE);

        entityManager.getTransaction().begin();

        entityManager.persist(product);

        entityManager.getTransaction().commit();

        entityManager.clear();

        Product productVerification = entityManager.find(Product.class, product.getId());
        Assert.assertTrue(productVerification.getActive());
    }
}