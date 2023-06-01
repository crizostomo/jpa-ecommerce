package com.jpa.ecommerce.nativeQuerySearchTest;

import com.jpa.ecommerce.EntityManagerTest;
import jakarta.persistence.Query;
import org.junit.Test;

import java.util.List;


public class NativeSearchTest extends EntityManagerTest {

    @Test
    public void executeSQL() {
        String sql = "select id, name from product";

        Query query = entityManager.createNativeQuery(sql);

        List<Object[]> list = query.getResultList();

        list.stream().forEach(arr -> System.out.println(String.format("Product => ID: %s, Name: %s", arr[0], arr[1])));
    }
}
