package com.jpa.ecommerce.jpql;

import com.jpa.ecommerce.EntityManagerTest;
import com.jpa.ecommerce.model.Product;
import jakarta.persistence.TypedQuery;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class NamedQueryTest extends EntityManagerTest {

    @Test
    public void executeSearch() {
        TypedQuery<Product> typedQueryList = entityManager.createNamedQuery("Product.list", Product.class);
        TypedQuery<Product> typedQueryListByCategory = entityManager.createNamedQuery("Product.listByCategories", Product.class);

        typedQueryListByCategory.setParameter("category", 2);

        List<Product> list = typedQueryList.getResultList();
        List<Product> listByCategory = typedQueryListByCategory.getResultList();

        Assert.assertFalse(list.isEmpty());
        Assert.assertFalse(listByCategory.isEmpty());
    }
}
