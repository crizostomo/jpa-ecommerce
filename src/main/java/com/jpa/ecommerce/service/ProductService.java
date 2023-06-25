package com.jpa.ecommerce.service;

import com.jpa.ecommerce.model.Product;
import com.jpa.ecommerce.repository.Products;
import jakarta.transaction.Transactional;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.time.LocalDateTime;
import java.util.Map;

@Service
public class ProductService {

    @Autowired
    private Products products;

    @Transactional
    public Product create(Product product) {
        product.setCreationDate(LocalDateTime.now());

        return products.save(product);
    }

    @Transactional
    public Product update(Integer id, Map<String, Object> product) {
        Product updatedProduct = products.search(id);

        try {
            BeanUtils.populate(updatedProduct, product);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }

        updatedProduct.setLastUpdateDate(LocalDateTime.now());

        return products.save(updatedProduct);
    }
}