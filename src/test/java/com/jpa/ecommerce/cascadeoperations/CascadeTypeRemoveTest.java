package com.jpa.ecommerce.cascadeoperations;

import com.jpa.ecommerce.EntityManagerTest;
import com.jpa.ecommerce.model.Order;
import com.jpa.ecommerce.model.OrderItem;
import com.jpa.ecommerce.model.OrderItemId;
import com.jpa.ecommerce.model.Product;
import org.junit.Assert;
import org.junit.Test;

public class CascadeTypeRemoveTest extends EntityManagerTest {

//    @Test
    public void removeOrphanItems() {
        Order order = entityManager.find(Order.class, 1);

        Assert.assertFalse(order.getOrderItems().isEmpty());

        entityManager.getTransaction().begin();
        order.getOrderItems().clear();
        entityManager.getTransaction().commit();

        entityManager.clear();

        Order orderVerification = entityManager.find(Order.class, order.getId());
        Assert.assertTrue(orderVerification.getOrderItems().isEmpty());
    }

    @Test
    public void removeProductCategoryRelationship() {
        Product product = entityManager.find(Product.class, 1);

        Assert.assertFalse(product.getCategories().isEmpty());

        entityManager.getTransaction().begin();
        product.getCategories().clear();
//        product.getCategories().remove(0); // Another way to remove, but at this time just one category
        entityManager.getTransaction().commit();

        entityManager.clear();

        Product productVerification = entityManager.find(Product.class, product.getId());
        Assert.assertTrue(productVerification.getCategories().isEmpty());
    }

    //    @Test
    public void removeOrderAndItems() {
        Order order = entityManager.find(Order.class, 1);

        entityManager.getTransaction().begin();
//        order.getOrderItems().forEach(i -> entityManager.remove(i)); // A way to remove without using Cascade
        entityManager.remove(order); // It is needed CascadeType.REMOVE in the attribute "items".
        entityManager.getTransaction().commit();

        entityManager.clear();

        Order orderVerification = entityManager.find(Order.class, order.getId());
        Assert.assertNull(orderVerification);
    }

    //     @Test
    public void removeOrderItemAndOrder() {
        OrderItem orderItem = entityManager.find(
                OrderItem.class, new OrderItemId(1, 1));

        entityManager.getTransaction().begin();
        entityManager.remove(orderItem); // It is needed CascadeType.REMOVE in the attribute "order".
        entityManager.getTransaction().commit();

        entityManager.clear();

        Order orderVerification = entityManager.find(Order.class, orderItem.getOrder().getId());
        Assert.assertNull(orderVerification);
    }
}