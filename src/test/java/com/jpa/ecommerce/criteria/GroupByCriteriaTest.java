package com.jpa.ecommerce.criteria;

import com.jpa.ecommerce.EntityManagerTest;
import com.jpa.ecommerce.model.*;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class GroupByCriteriaTest extends EntityManagerTest {

    @Test
    public void groupResult2() {
        // Total amount of sales by category
        // String jpql = "select c.name, sum(oi.productPrice) from OrderItem oi " +
        // "join oi.product pro join pro.categories c " +
        // "group by c.id";

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Object[]> criteriaQuery = criteriaBuilder.createQuery(Object[].class);
        Root<OrderItem> root = criteriaQuery.from(OrderItem.class);
        Join<OrderItem, Product> orderItemProductJoin = root.join(OrderItem_.product);
        Join<Product, Category> productCategoryJoin = orderItemProductJoin.join(Product_.categories);

        criteriaQuery.multiselect(productCategoryJoin.get(Category_.name),
                criteriaBuilder.sum(root.get(OrderItem_.productPrice))
        );

        criteriaQuery.groupBy(productCategoryJoin.get(Category_.id));

        TypedQuery<Object[]> typedQuery = entityManager.createQuery(criteriaQuery);
        List<Object[]> list = typedQuery.getResultList();
        Assert.assertFalse(list.isEmpty());

        list.forEach(arr -> System.out.println("Category Name: " + arr[0] + ", Sum: " + arr[1]));
    }

    @Test
    public void groupResult1() {
        // Quantity of products by category
        // String jpql = "select c.name, count (p.id) from Category c join c.products p group by c.id";

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Object[]> criteriaQuery = criteriaBuilder.createQuery(Object[].class);
        Root<Category> root = criteriaQuery.from(Category.class);
        Join<Category, Product> categoryProductJoin = root.join(Category_.products, JoinType.LEFT);

        criteriaQuery.multiselect(root.get(Category_.name),
                criteriaBuilder.count(categoryProductJoin.get(Product_.id))
        );

        criteriaQuery.groupBy(root.get(Category_.id));

        TypedQuery<Object[]> typedQuery = entityManager.createQuery(criteriaQuery);
        List<Object[]> list = typedQuery.getResultList();
        Assert.assertFalse(list.isEmpty());

        list.forEach(arr -> System.out.println("Name: " + arr[0] + ", Count: " + arr[1]));
    }
}
