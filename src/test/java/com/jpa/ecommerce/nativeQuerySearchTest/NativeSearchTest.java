package com.jpa.ecommerce.nativeQuerySearchTest;

import com.jpa.ecommerce.EntityManagerTest;
import com.jpa.ecommerce.model.Product;
import jakarta.persistence.Query;
import org.junit.Test;

import java.util.List;


public class NativeSearchTest extends EntityManagerTest {

    @Test
    public void inputParameters() {
        String sql = "select prd_id id, prd_name name, prd_description description, prd_creation_date creation_date, " +
                     "prd_last_update_date prd_last_update_date, prd_price price, prd_photo photo from product_ecm where prd_id = :id";

        Query query = entityManager.createNativeQuery(sql, Product.class);
        query.setParameter("id", 201);

        List<Product> list = query.getResultList();

        list.stream().forEach(obj -> System.out.println(String.format("Product => ID: %s, Name: %s", obj.getId(), obj.getName())));
    }

    @Test
    public void executeSQLReturningEntity() {
        String sql = "select id, name, description, creation_date, last_update_date, price, photo from product";
//        String sql = "select id, name, description, creation_date, last_update_date, price, photo from store_product";
//        String sql = "select prd_id id, prd_name name, prd_description description, prd_creation_date creation_date,
//        prd_last_update_date prd_last_update_date, prd_price price, prd_photo photo from product_ecm"; // We need an alias if the columns name are different
//        String sql = "select id, name, description, null creation_date, null last_update_date, price, null photo from product_erp"; // If we don't have all columns required


        Query query = entityManager.createNativeQuery(sql, Product.class);

        List<Product> list = query.getResultList();

        list.stream().forEach(obj -> System.out.println(String.format("Product => ID: %s, Name: %s", obj.getId(), obj.getName())));
    }

    @Test
    public void executeSQL() {
        String sql = "select id, name from product";

        Query query = entityManager.createNativeQuery(sql);

        List<Object[]> list = query.getResultList();

        list.stream().forEach(arr -> System.out.println(String.format("Product => ID: %s, Name: %s", arr[0], arr[1])));
    }
}
