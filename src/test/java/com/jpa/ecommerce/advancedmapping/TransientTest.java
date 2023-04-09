package com.jpa.ecommerce.advancedmapping;

import com.jpa.ecommerce.EntityManagerTest;
import com.jpa.ecommerce.model.Client;
import org.junit.Assert;
import org.junit.Test;

public class TransientTest extends EntityManagerTest {

    @Test
    public void validateFirstName() {
        Client client = entityManager.find(Client.class, 1);

        Assert.assertEquals("Roronoa", client.getFirstName());
    }
}
