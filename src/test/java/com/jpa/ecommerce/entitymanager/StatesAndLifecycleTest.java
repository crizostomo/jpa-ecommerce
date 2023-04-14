package com.jpa.ecommerce.entitymanager;

import com.jpa.ecommerce.EntityManagerTest;
import com.jpa.ecommerce.model.Category;
import org.junit.Test;

public class StatesAndLifecycleTest extends EntityManagerTest {

    @Test
    public void analyseStates() {
        Category newCategory = new Category();
        newCategory.setName("Electronics");

        Category ManagedCategoryMerge = entityManager.merge(newCategory);

        Category managedCategory = entityManager.find(Category.class, 1);

        entityManager.remove(managedCategory);
        entityManager.persist(managedCategory);

        entityManager.detach(managedCategory);
    }
}