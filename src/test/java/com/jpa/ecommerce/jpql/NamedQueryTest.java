package com.jpa.ecommerce.jpql;

import com.jpa.ecommerce.EntityManagerTest;
import com.jpa.ecommerce.model.Order;
import com.jpa.ecommerce.model.Product;
import jakarta.persistence.TypedQuery;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class NamedQueryTest extends EntityManagerTest {

    @Test
    public void executeSearchBySpecificProductXMLFile() {
        TypedQuery<Product> typedQueryList = entityManager.createNamedQuery("Product.all", Product.class);

        List<Product> list = typedQueryList.getResultList();

        Assert.assertFalse(list.isEmpty());
    }

    @Test
    public void executeSearchBySpecificOrderXMLFile() {
        TypedQuery<Order> typedQueryList = entityManager.createNamedQuery("Order.search", Order.class);

        List<Order> list = typedQueryList.getResultList();

        Assert.assertFalse(list.isEmpty());
    }

    @Test
    public void executeSearchFileXML() {
        TypedQuery<Order> typedQueryList = entityManager.createNamedQuery("Order.list", Order.class);

        List<Order> list = typedQueryList.getResultList();

        Assert.assertFalse(list.isEmpty());
    }

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
