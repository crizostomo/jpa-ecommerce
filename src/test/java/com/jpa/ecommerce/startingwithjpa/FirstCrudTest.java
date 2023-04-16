package com.jpa.ecommerce.startingwithjpa;

import com.jpa.ecommerce.EntityManagerTest;
import com.jpa.ecommerce.model.Client;
import com.jpa.ecommerce.model.Gender;
import com.jpa.ecommerce.model.Product;
import org.junit.Assert;
import org.junit.Test;

public class FirstCrudTest extends EntityManagerTest {

    @Test
    public void insertData() {
        Client client = new Client();

//        client.setId(3);
        client.setName("Zoro");
        client.setGender(Gender.MALE);
        client.setCpf("3334");

        entityManager.getTransaction().begin();
        entityManager.persist(client);
        entityManager.getTransaction().commit();

        entityManager.clear();

        Client clientVerification = entityManager.find(Client.class, client.getId());
        Assert.assertNotNull(clientVerification);
    }

    @Test
    public void searchByIdentifier() {
        Product product = entityManager.find(Product.class, 1);

        Assert.assertNotNull(product);
        Assert.assertEquals("Kindle", product.getName());
    }

    @Test
    public void updateData() {
        Client client = new Client();

        client.setId(1);
        client.setName("Luffy");
        client.setCpf("000");
        client.setGender(Gender.MALE);

        entityManager.getTransaction().begin();
        entityManager.merge(client);
        entityManager.getTransaction().commit();

        entityManager.clear();

        Client clientVerification = entityManager.find(Client.class, client.getId());
        Assert.assertEquals("Luffy", clientVerification.getName());
    }

    @Test
    public void removeData() {
        Client client = entityManager.find(Client.class, 2);

        entityManager.getTransaction().begin();
        entityManager.remove(client);
        entityManager.getTransaction().commit();

        entityManager.clear();

        Client clientVerification = entityManager.find(Client.class, client.getId());
        Assert.assertNull(clientVerification);
    }
}