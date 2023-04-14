package com.jpa.ecommerce.basicmapping;

import com.jpa.ecommerce.EntityManagerTest;
import com.jpa.ecommerce.model.Client;
import com.jpa.ecommerce.model.Gender;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

public class MappingEnumsTest extends EntityManagerTest {

    @Test
    public void testEnum() {
        Client client = new Client();
        client.setId(4);
        client.setName("Zoro");
        client.setGender(Gender.MALE);
        client.setCpf("123");

        entityManager.getTransaction().begin();
        entityManager.persist(client);
        entityManager.getTransaction().commit();

        entityManager.clear();

        Client clientVerification = entityManager.find(Client.class, client.getId());
        Assert.assertNotNull(clientVerification);
    }
}
