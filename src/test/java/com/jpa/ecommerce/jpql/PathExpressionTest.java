package com.jpa.ecommerce.jpql;

import com.jpa.ecommerce.EntityManagerTest;
import com.jpa.ecommerce.model.Invoice;
import com.jpa.ecommerce.model.Order;
import com.jpa.ecommerce.model.PaymentStatus;
import jakarta.persistence.TemporalType;
import jakarta.persistence.TypedQuery;
import org.junit.Assert;
import org.junit.Test;

import java.util.Date;
import java.util.List;

public class PathExpressionTest extends EntityManagerTest {

    @Test
    public void usingParameterDate() {
        String jpql = "select nf from Invoice nf where nf.issuingDate <= ?1";

        TypedQuery<Invoice> typedQuery = entityManager.createQuery(jpql, Invoice.class);
        typedQuery.setParameter(1, new Date(), TemporalType.TIMESTAMP);

        List<Invoice> list = typedQuery.getResultList();
        Assert.assertTrue(list.size() == 1);
    }

    @Test
    public void usingParameter() {
//        String jpql = "select o from Order o join o.payment pay " +
//                "where o.id = ?1 and pay.status = ?2"; //?1 is putting a reference to the position
        String jpql = "select o from Order o join o.payment pay " +
                "where o.id = :orderId and pay.status = :statusPayment";

        TypedQuery<Order> typedQuery = entityManager.createQuery(jpql, Order.class);
        typedQuery.setParameter("orderId", 2);
        typedQuery.setParameter("statusPayment", PaymentStatus.PROCESSING);

        List<Order> list = typedQuery.getResultList();
        Assert.assertTrue(list.size() == 1);
    }

    @Test
    public void searchOrdersWithASpecificProduct() {
        String jpql = "select o from Order o join o.orderItems i where i.id.productId = 1";
//        String jpql = "select o from Order o join o.orderItems i where i.product.id = 1";
//        String jpql = "select o from Order o join o.orderItems i join i.product pro where pro.id = 1";

        TypedQuery<Order> typedQuery = entityManager.createQuery(jpql, Order.class);

        List<Order> list = typedQuery.getResultList();
        Assert.assertTrue(list.size() == 2);
    }

    @Test
    public void usePathExpressions() {
        String jpql = "select o.client.name from Order o";

        TypedQuery<Object[]> typedQuery = entityManager.createQuery(jpql, Object[].class);

        List<Object[]> list = typedQuery.getResultList();
        Assert.assertFalse(list.isEmpty());
    }
}
