package com.jpa.ecommerce.advancedmapping;

import com.jpa.ecommerce.EntityManagerTest;
import com.jpa.ecommerce.model.Client;
import com.jpa.ecommerce.model.Gender;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDate;

public class SecondaryTableTest extends EntityManagerTest {

    @Test
    public void saveClient() {
        Client client = new Client();
        client.setName("Roronoa Zoro");
        client.setCpf("555");
        client.setGender(Gender.MALE);
        client.setBirthDate(LocalDate.of(1991, 11, 1991));

        entityManager.getTransaction().begin();
        entityManager.persist(client);
        entityManager.getTransaction().commit();

        entityManager.clear();

        Client clientVerification = entityManager.find(Client.class, client.getId());
        Assert.assertNotNull(clientVerification.getGender());
    }
}
