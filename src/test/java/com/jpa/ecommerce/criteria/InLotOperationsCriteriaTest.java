package com.jpa.ecommerce.criteria;

import com.jpa.ecommerce.EntityManagerTest;
import com.jpa.ecommerce.model.Category;
import com.jpa.ecommerce.model.Category_;
import com.jpa.ecommerce.model.Product;
import com.jpa.ecommerce.model.Product_;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.*;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.List;

public class InLotOperationsCriteriaTest extends EntityManagerTest {

    @Test
    public void updatingInLot() {
        entityManager.getTransaction().begin();

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaUpdate<Product> criteriaUpdate = criteriaBuilder.createCriteriaUpdate(Product.class);
        Root<Product> root = criteriaUpdate.from(Product.class);

        Expression<BigDecimal> updatedPrice = criteriaBuilder.prod(root.get(Product_.price), new BigDecimal("1.1"));
        criteriaUpdate.set(root.get(Product_.price), updatedPrice);

        Subquery<Integer> subquery = criteriaUpdate.subquery(Integer.class);
        Root<Product> subqueryRoot = subquery.correlate(root);
        Join<Product, Category> productCategoryJoin = subqueryRoot.join(Product_.categories);
        subquery.select(criteriaBuilder.literal(1));
        subquery.where(criteriaBuilder.equal(productCategoryJoin.get(Category_.id), 2));

        criteriaUpdate.where(criteriaBuilder.exists(subquery));

        Query updateQuery = entityManager.createQuery(criteriaUpdate);
        int updatedCount = updateQuery.executeUpdate();

        entityManager.getTransaction().commit();

        // Retrieve the updated prices
        CriteriaQuery<BigDecimal> priceQuery = criteriaBuilder.createQuery(BigDecimal.class);
        Root<Product> priceRoot = priceQuery.from(Product.class);
        priceQuery.select(priceRoot.get(Product_.price));
        List<BigDecimal> updatedPrices = entityManager.createQuery(priceQuery).getResultList();

        System.out.println("Updated prices: " + updatedPrices);
        System.out.println("Number of entities updated: " + updatedCount);
    }
}
