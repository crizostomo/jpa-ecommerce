package com.jpa.ecommerce.relationships;

import com.jpa.ecommerce.EntityManagerTest;
import com.jpa.ecommerce.model.*;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;

public class RelationshipManyToManyTest extends EntityManagerTest {

    @Test
    public void verifyRelationship() {
        Product product = entityManager.find(Product.class, 1);
        Category category = entityManager.find(Category.class, 1);

        entityManager.getTransaction().begin();
//        category.setProducts(Arrays.asList(product));
        product.setCategories(Arrays.asList(category));
        entityManager.getTransaction().commit();

        entityManager.clear();

        Category categoryVerification = entityManager.find(Category.class, category.getId());
        Assert.assertFalse(categoryVerification.getProducts().isEmpty());
    }
}
