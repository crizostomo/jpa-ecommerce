package com.jpa.ecommerce.basicmapping;

import com.jpa.ecommerce.EntityManagerTest;
import com.jpa.ecommerce.model.Client;
import com.jpa.ecommerce.model.DeliveryAddress;
import com.jpa.ecommerce.model.Order;
import com.jpa.ecommerce.model.OrderStatus;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class MappingEmbeddedObjectTest extends EntityManagerTest {

    @Test
    public void analyseEmbeddedObject() {
        Client client = entityManager.find(Client.class, 1);

        DeliveryAddress address = new DeliveryAddress();
        address.setZipCode("00000-00");
        address.setStreet("Street xyz");
        address.setNumber("123");
        address.setNeighborhood("Downtown");
        address.setCity("New Jersey - II");
        address.setState("New Jersey");

        Order order = new Order();
        order.setCreationDate(LocalDateTime.now());
        order.setStatus(OrderStatus.WAITING);
        order.setTotal(new BigDecimal(1000));
        order.setDeliveryAddress(address);
        order.setClient(client);

        entityManager.getTransaction().begin();
        entityManager.persist(order);
        entityManager.getTransaction().commit();

        entityManager.clear();

        Order orderVerification = entityManager.find(Order.class, order.getId());
        Assert.assertNotNull(orderVerification);
        Assert.assertNotNull(orderVerification.getDeliveryAddress());
        Assert.assertNotNull(orderVerification.getDeliveryAddress().getZipCode());
    }
}