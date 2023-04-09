package com.jpa.ecommerce.advancedmapping;

import com.jpa.ecommerce.EntityManagerTest;
import com.jpa.ecommerce.model.Attribute;
import com.jpa.ecommerce.model.Client;
import com.jpa.ecommerce.model.Product;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;

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

    @Test
    public void applyingContactMap() {
        entityManager.getTransaction().begin();

        Client client = entityManager.find(Client.class, 1);
        client.setContact(Collections.singletonMap("email", "roronoa@onepiece.com"));

        entityManager.getTransaction().commit();

        entityManager.clear();

        Client clientVerification = entityManager.find(Client.class, client.getId());
        Assert.assertEquals("roronoa@onepiece.com", clientVerification.getContact().get("email"));
    }
}
