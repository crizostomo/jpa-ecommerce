package com.jpa.ecommerce.criteria;

import com.jpa.ecommerce.EntityManagerTest;
import com.jpa.ecommerce.model.Product;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class MetaModelTest extends EntityManagerTest {


    // Class 10.13 Needs to be updated, there is an issue by generating the classes
    @Test
    public void metaModel() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Product> criteriaQuery = criteriaBuilder.createQuery(Product.class);
        Root<Product> root = criteriaQuery.from(Product.class);

        criteriaQuery.select(root);

        criteriaQuery.where(criteriaBuilder.or(
                criteriaBuilder.like(root.get("name"), "%C%"),
                criteriaBuilder.like(root.get("description"), "%C%")
        ));

        TypedQuery<Product> typedQuery = entityManager.createQuery(criteriaQuery);

        List<Product> productList = typedQuery.getResultList();
        Assert.assertFalse(productList.isEmpty());
    }
}
