package com.jpa.ecommerce.advancedmapping;

import com.jpa.ecommerce.EntityManagerTest;
import com.jpa.ecommerce.model.*;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class InheritanceTest extends EntityManagerTest {

    @Test
    public void saveClient() {
        Client client = new Client();
        client.setName("Monkey D. Luffy");
        client.setGender(Gender.MALE);
        client.setCpf("333");

        entityManager.getTransaction().begin();
        entityManager.persist(client);
        entityManager.getTransaction().commit();

        entityManager.clear();

        Client clientVerification = entityManager.find(Client.class, client.getId());
        Assert.assertNotNull(clientVerification.getId());
    }

    @Test
    public void searchPayment() {
        List<Payment> payments = entityManager
                .createQuery("select p from Payment p")
                .getResultList();
    }

    @Test
    public void includePaymentOrder() {
        Order order = entityManager.find(Order.class, 1);

        CardPayment cardPayment = new CardPayment();
        cardPayment.setOrder(order);
        cardPayment.setStatus(PaymentStatus.PROCESSING);
        cardPayment.setCardNumber("123");

        entityManager.getTransaction().begin();
        entityManager.persist(cardPayment);
        entityManager.getTransaction().commit();

        entityManager.clear();

        Order orderVerification = entityManager.find(Order.class, order.getId());
        Assert.assertNotNull(orderVerification.getPayment());
    }
}
