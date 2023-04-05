package com.jpa.ecommerce.relations;

import com.jpa.ecommerce.EntityManagerTest;
import com.jpa.ecommerce.model.Category;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

public class AutoRelationTest extends EntityManagerTest {

    @Test
    public void verifyRelation() {
        Category fatherCategory = new Category();
        fatherCategory.setName("Electronics");

        Category category = new Category();
        fatherCategory.setName("Cell phones");
        category.setFatherCategory(fatherCategory);


        entityManager.getTransaction().begin();
        entityManager.persist(fatherCategory);
        entityManager.persist(category);

        entityManager.getTransaction().commit();

        entityManager.clear();

        Category verificationCategory = entityManager.find(Category.class, category.getId());
        Assert.assertNotNull(verificationCategory.getFatherCategory());

        Category verificationFatherCategory = entityManager.find(Category.class, fatherCategory.getId());
        Assert.assertFalse(verificationFatherCategory.getCategories().isEmpty());
    }
}
