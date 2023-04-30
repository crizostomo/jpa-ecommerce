package com.jpa.ecommerce.jpql;

import com.jpa.ecommerce.EntityManagerTest;
import com.jpa.ecommerce.model.Product;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.stream.Collectors;

public class InLotOperationsTest extends EntityManagerTest {
    private static final int INSERT_LIMIT = 4;

    @Test
    public void insertInLot() throws IOException {
        InputStream in = InLotOperationsTest.class.getClassLoader()
                .getResourceAsStream("products/import.txt");

        BufferedReader reader = new BufferedReader(new InputStreamReader(in));

        entityManager.getTransaction().begin();

        int insertCount = 0;

        for(String line: reader.lines().collect(Collectors.toList())) {
            if (line.isBlank()) {
                continue;
            }

            String[] columnProduct = line.split(";");
            Product product = new Product();
            product.setName(columnProduct[0]);
            product.setDescription(columnProduct[1]);
            product.setPrice(new BigDecimal(columnProduct[2]));
            product.setCreationDate(LocalDateTime.now());

            entityManager.persist(product);

            if (++insertCount == INSERT_LIMIT) {
                entityManager.flush();
                entityManager.clear();

                insertCount = 0;

                System.out.println("---------------------------------");
            }
        }

        entityManager.getTransaction().commit();
    }

}
