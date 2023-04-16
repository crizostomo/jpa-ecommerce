package com.jpa.ecommerce.advancedmapping;

import com.jpa.ecommerce.EntityManagerTest;
import com.jpa.ecommerce.model.Product;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class ColumnDetailTest extends EntityManagerTest {

    @Test
    public void avoidInsertionOfTheUpdatedColumn() {
        Product product = new Product();
        product.setName("Keyboard for smartphones");
        product.setDescription("The most comfortable");
        product.setPrice(BigDecimal.ONE);
        product.setCreationDate(LocalDateTime.now());
        product.setLastUpdateDate(LocalDateTime.now());

        entityManager.getTransaction().begin();
        entityManager.persist(product);
        entityManager.getTransaction().commit();

        entityManager.clear();

        Product productVerification = entityManager.find(Product.class, product.getId());
        Assert.assertNotNull(productVerification.getCreationDate());
        Assert.assertNotNull(productVerification.getLastUpdateDate());
    }

    @Test
    public void avoidUpdateOfTheCreationColumn() {
        entityManager.getTransaction().begin();

        Product product = entityManager.find(Product.class, 1);
        product.setPrice(BigDecimal.TEN);
        product.setCreationDate(LocalDateTime.now());
        product.setLastUpdateDate(LocalDateTime.now());

        entityManager.getTransaction().commit();

        entityManager.clear();

        Product productVerification = entityManager.find(Product.class, product.getId());
        Assert.assertEquals(product.getCreationDate().truncatedTo(ChronoUnit.SECONDS),
                productVerification.getCreationDate().truncatedTo(ChronoUnit.SECONDS));
        Assert.assertEquals(product.getLastUpdateDate().truncatedTo(ChronoUnit.SECONDS),
                productVerification.getLastUpdateDate().truncatedTo(ChronoUnit.SECONDS));
    }
}