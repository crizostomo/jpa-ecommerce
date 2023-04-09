package com.jpa.ecommerce.advancedmapping;

import com.jpa.ecommerce.EntityManagerTest;
import com.jpa.ecommerce.model.Attribute;
import com.jpa.ecommerce.model.Product;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

public class ElementCollectionTest extends EntityManagerTest {

    @Test
    public void applyingTags() {
        entityManager.getTransaction().begin();

        Product product = entityManager.find(Product.class, 1);
        product.setTags(Arrays.asList("tag-example-1", "tag-example-2"));

        entityManager.getTransaction().commit();

        entityManager.clear();

        Product productVerification = entityManager.find(Product.class, product.getId());
        Assert.assertFalse(productVerification.getTags().isEmpty());
    }

    @Test
    public void applyingAttributes() {
        entityManager.getTransaction().begin();

        Product product = entityManager.find(Product.class, 1);
        product.setAttributes(Arrays.asList(new Attribute("screen", "320x600")));

        entityManager.getTransaction().commit();

        entityManager.clear();

        Product productVerification = entityManager.find(Product.class, product.getId());
        Assert.assertFalse(productVerification.getAttributes().isEmpty());
    }
}
