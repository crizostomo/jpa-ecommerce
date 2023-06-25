package com.jpa.ecommerce.repository;

import com.jpa.ecommerce.model.Product;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class Products {

    @PersistenceContext
    private EntityManager entityManager;

    public Product search(Integer id) {
        return entityManager.find(Product.class, id);
    }

    public Product save(Product product) {
        return entityManager.merge(product);
    }

    public List<Product> list() {
        return entityManager
                .createQuery("select p from Product p", Product.class)
                .getResultList();
    }
}