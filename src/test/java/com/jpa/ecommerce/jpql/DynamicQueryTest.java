package com.jpa.ecommerce.jpql;

import com.jpa.ecommerce.EntityManagerTest;
import com.jpa.ecommerce.model.Product;
import jakarta.persistence.TypedQuery;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class DynamicQueryTest extends EntityManagerTest {

    @Test
    public void executeDynamicSearch() {
        Product searched = new Product();
        searched.setName("GoPro Camera");

        List<Product> list = search(searched);

        Assert.assertFalse(list.isEmpty());
        Assert.assertEquals("GoPro Camera Hero 7", list.get(0).getName());
    }

    private List<Product> search(Product searched) {
        StringBuilder jpql = new StringBuilder("select p from Product p where 1 = 1");

        if (searched.getName() != null) {
            jpql.append(" and p.name like concat('%', :name, '%')");
        }

        if (searched.getDescription() != null) {
            jpql.append(" and p.description like concat('%', :description, '%')");
        }

        TypedQuery<Product> typedQuery = entityManager.createQuery(jpql.toString(), Product.class);

        if (searched.getName() != null) {
            typedQuery.setParameter("name", searched.getName());
        }

        if (searched.getDescription() != null) {
            typedQuery.setParameter("description", searched.getDescription());
        }

        return typedQuery.getResultList();
    }
}
