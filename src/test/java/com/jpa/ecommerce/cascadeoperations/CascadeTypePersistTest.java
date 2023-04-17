package com.jpa.ecommerce.cascadeoperations;

import com.jpa.ecommerce.EntityManagerTest;
import com.jpa.ecommerce.model.*;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;

public class CascadeTypePersistTest extends EntityManagerTest {

    // @Test
    public void persistOrderWithItems() {
        Client client = entityManager.find(Client.class, 1);
        Product product = entityManager.find(Product.class, 1);

        Order order = new Order();
        order.setCreationDate(LocalDateTime.now());
        order.setClient(client);
        order.setTotal(product.getPrice());
        order.setStatus(OrderStatus.WAITING);

        OrderItem orderItem = new OrderItem();
        orderItem.setId(new OrderItemId());
        orderItem.setOrder(order);
        orderItem.setProduct(product);
        orderItem.setQuantity(1);
        orderItem.setProductPrice(product.getPrice());

        order.setOrderItems(Arrays.asList(orderItem)); // CascadeType.PERSIST

        entityManager.getTransaction().begin();
        entityManager.persist(order);
        entityManager.getTransaction().commit();

        entityManager.clear();

        Order orderVerification = entityManager.find(Order.class, order.getId());
        Assert.assertNotNull(order);
        Assert.assertFalse(order.getOrderItems().isEmpty());

    }

    @Test
    public void persistOrderItemWithOrder() {
        Client client = entityManager.find(Client.class, 1);
        Product product = entityManager.find(Product.class, 1);

        Order order = new Order();
        order.setCreationDate(LocalDateTime.now());
        order.setClient(client);
        order.setTotal(product.getPrice());
        order.setStatus(OrderStatus.WAITING);

        OrderItem orderItem = new OrderItem();
        orderItem.setId(new OrderItemId());
        orderItem.setOrder(order);// There's no need for CascadeType.PERSIST since it has @MapsId.
        orderItem.setProduct(product);
        orderItem.setQuantity(1);
        orderItem.setProductPrice(product.getPrice());

        entityManager.getTransaction().begin();
        entityManager.persist(orderItem);
        entityManager.getTransaction().commit();

        entityManager.clear();

        Order orderVerification = entityManager.find(Order.class, order.getId());
        Assert.assertNotNull(order);
    }

    // @Test
    public void persistOrderWithClient() {
        Client client = new Client();
        client.setBirthDate(LocalDate.of(1980, 1, 1));
        client.setGender(Gender.MALE);
        client.setName("Zoro");
        client.setCpf("01234567890");

        Order order = new Order();
        order.setCreationDate(LocalDateTime.now());
        order.setClient(client); // CascadeType.PERSIST
        order.setTotal(BigDecimal.ZERO);
        order.setStatus(OrderStatus.WAITING);

        entityManager.getTransaction().begin();
        entityManager.persist(order);
        entityManager.getTransaction().commit();

        entityManager.clear();

        Client clientVerification = entityManager.find(Client.class, client.getId());
        Assert.assertNotNull(client);
    }
}