package com.jpa.ecommerce.jpql;

import com.jpa.ecommerce.EntityManagerTest;
import com.jpa.ecommerce.model.Category;
import jakarta.persistence.TypedQuery;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class PaginationJPQLTest extends EntityManagerTest {

    @Test
    public void paginateResults() {
        String jpql = "select c from Category c order by c.name asc";

        TypedQuery<Category> typedQuery = entityManager.createQuery(jpql, Category.class);

        // TO GO TO OTHER PAGES: FIRST_RESULT = MAX_RESULTS * (page - 1)
        typedQuery.setFirstResult(0); // To limit the number of results, just use setMaxResults
        typedQuery.setMaxResults(3);

        List<Category> list = typedQuery.getResultList();
        Assert.assertFalse(list.isEmpty());

        list.forEach(category -> System.out.println(category.getId() + ", " + category.getName()));
    }
}
