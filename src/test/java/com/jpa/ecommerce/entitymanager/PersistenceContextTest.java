package com.jpa.ecommerce.entitymanager;

import com.jpa.ecommerce.EntityManagerTest;
import com.jpa.ecommerce.model.Product;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class PersistenceContextTest extends EntityManagerTest {

    @Test
    public void usePersistenceContext() {
        entityManager.getTransaction().begin();

        Product product = entityManager.find(Product.class, 1);
        product.setPrice(new BigDecimal(100.0));

        Product product2 = new Product();
        product2.setCreationDate(LocalDateTime.now());
        product2.setName("Coffee Mug");
        product2.setPrice(new BigDecimal(10.0));
        product2.setDescription("A good mug for your Dad");
        entityManager.persist(product2);

        Product product3 = new Product();
        product3.setCreationDate(LocalDateTime.now());
        product3.setName("Tea Mug");
        product3.setPrice(new BigDecimal(10.0));
        product2.setDescription("A good mug for your Mom");
        product3 = entityManager.merge(product3);

        entityManager.flush();

        product3.setDescription("Alter description");

        entityManager.getTransaction().commit();
    }
}