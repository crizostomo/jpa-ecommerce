package com.jpa.ecommerce.basicmapping;

import com.jpa.ecommerce.EntityManagerTest;
import com.jpa.ecommerce.model.Category;
import org.junit.Assert;
import org.junit.Test;

public class PrimaryKeyStrategyTest extends EntityManagerTest {

    @Test
    public void testingKeyStrategy() {
        Category category = new Category();
        category.setName("Swimming");

        entityManager.getTransaction().begin();
        entityManager.persist(category);
        entityManager.getTransaction().commit();

        entityManager.clear();

        Category categoryVerification = entityManager.find(Category.class, category.getId());
        Assert.assertNotNull(categoryVerification);
    }
}