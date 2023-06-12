package com.jpa.ecommerce.importantdetails;

import com.jpa.ecommerce.EntityManagerTest;
import com.jpa.ecommerce.model.Client;
import org.junit.Test;

public class ValidationTest extends EntityManagerTest {

    @Test
    public void validateClient() {
        entityManager.getTransaction().begin();

        Client client = new Client();
        entityManager.merge(client);

        entityManager.getTransaction().commit();
    }
}