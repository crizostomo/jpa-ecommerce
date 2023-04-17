package com.jpa.ecommerce.cascadeoperations;

import com.jpa.ecommerce.EntityManagerTest;
import com.jpa.ecommerce.model.*;
import org.junit.Assert;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;

public class CascadeTypeMergeTest extends EntityManagerTest {

    // @Test
    public void updateProductWithCategory() {
        Product product = new Product();
        product.setId(1);
        product.setLastUpdateDate(LocalDateTime.now());
        product.setPrice(new BigDecimal(500));
        product.setName("Kindle");
        product.setDescription("The x-version.");

        Category category = new Category();
        category.setId(2);
        category.setName("Tablets");

        product.setCategories(Arrays.asList(category)); // CascadeType.MERGE

        entityManager.getTransaction().begin();
        entityManager.merge(product);
        entityManager.getTransaction().commit();

        entityManager.clear();

        Category categoryVerification = entityManager.find(Category.class, category.getId());
        Assert.assertEquals("Tablets", categoryVerification.getName());
    }

    // @Test
    public void updateOrderWithItems() {
        Client client = entityManager.find(Client.class, 1);
        Product product = entityManager.find(Product.class, 1);

        Order order = new Order();
        order.setId(1);
        order.setClient(client);
        order.setStatus(OrderStatus.WAITING);

        OrderItem orderItem = new OrderItem();
        orderItem.setId(new OrderItemId());
        orderItem.getId().setOrderId(order.getId());
        orderItem.getId().setProductId(product.getId());
        orderItem.setOrder(order);
        orderItem.setProduct(product);
        orderItem.setQuantity(3);
        orderItem.setProductPrice(product.getPrice());

        order.setOrderItems(Arrays.asList(orderItem)); // CascadeType.MERGE

        entityManager.getTransaction().begin();
        entityManager.merge(order);
        entityManager.getTransaction().commit();

        entityManager.clear();

        OrderItem orderItemVerification = entityManager.find(OrderItem.class, orderItem.getId());
        Assert.assertTrue(orderItemVerification.getQuantity().equals(3));
    }

    // @Test
    public void updateOrderItemWithOrder() {
        Client client = entityManager.find(Client.class, 1);
        Product product = entityManager.find(Product.class, 1);

        Order order = new Order();
        order.setId(1);
        order.setClient(client);
        order.setStatus(OrderStatus.PAID);

        OrderItem orderItem = new OrderItem();
        orderItem.setId(new OrderItemId());
        orderItem.getId().setOrderId(order.getId());
        orderItem.getId().setProductId(product.getId());
        orderItem.setOrder(order); // CascadeType.MERGE
        orderItem.setProduct(product);
        orderItem.setQuantity(5);
        orderItem.setProductPrice(product.getPrice());

        order.setOrderItems(Arrays.asList(orderItem));

        entityManager.getTransaction().begin();
        entityManager.merge(orderItem);
        entityManager.getTransaction().commit();

        entityManager.clear();

        OrderItem orderItemVerification = entityManager.find(OrderItem.class, orderItem.getId());
        Assert.assertTrue(OrderStatus.PAID.equals(orderItemVerification.getOrder().getStatus()));
    }
}