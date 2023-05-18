package com.jpa.ecommerce.criteria;

import com.jpa.ecommerce.EntityManagerTest;
import com.jpa.ecommerce.model.*;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Root;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class FunctionCriteriaTest extends EntityManagerTest {

    @Test
    public void applyAggregationFunction() { // Similar to the exercise in the Class FunctionTest - applyAggregationFunction
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Object[]> criteriaQuery = criteriaBuilder.createQuery(Object[].class);
        Root<Order> root = criteriaQuery.from(Order.class);

        criteriaQuery.multiselect(
                criteriaBuilder.count(root.get(Order_.id)),
                criteriaBuilder.avg(root.get(Order_.total)),
                criteriaBuilder.sum(root.get(Order_.total)),
                criteriaBuilder.min(root.get(Order_.total)),
                criteriaBuilder.max(root.get(Order_.total))
        );

        TypedQuery<Object[]> typedQuery = entityManager.createQuery(criteriaQuery);
        List<Object[]> list = typedQuery.getResultList();
        Assert.assertFalse(list.isEmpty());

        list.forEach(arr -> System.out.println(
                "count: " + arr[0]
                        + ", avg: " + arr[1]
                        + ", sum: " + arr[2]
                        + ", min: " + arr[2]
                        + ", max: " + arr[2]
        ));
    }

    @Test
    public void applyNativeFunction() { // Similar to the exercise in the Class FunctionTest - applyNativeFunction
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Object[]> criteriaQuery = criteriaBuilder.createQuery(Object[].class);
        Root<Order> root = criteriaQuery.from(Order.class);

        criteriaQuery.multiselect(root.get(Order_.id),
                criteriaBuilder.function("dayname", String.class, root.get(Order_.creationDate))
        );

        criteriaQuery.where(criteriaBuilder.isTrue(
                criteriaBuilder.function("invoice_above_average", Boolean.class, root.get(Order_.total))));

        TypedQuery<Object[]> typedQuery = entityManager.createQuery(criteriaQuery);
        List<Object[]> list = typedQuery.getResultList();
        Assert.assertFalse(list.isEmpty());

        list.forEach(arr -> System.out.println(
                arr[0]
                        + ", dayname: " + arr[1]
        ));
    }

    @Test
    public void applyCollectionFunction() { // Similar to the exercise in the Class FunctionTest - applyCollectionFunction
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Object[]> criteriaQuery = criteriaBuilder.createQuery(Object[].class);
        Root<Order> root = criteriaQuery.from(Order.class);

        criteriaQuery.multiselect(root.get(Order_.id),
                criteriaBuilder.size(root.get(Order_.orderItems))
        );

        criteriaQuery.where(criteriaBuilder.greaterThan(criteriaBuilder.size(root.get(Order_.orderItems)), 1));

        TypedQuery<Object[]> typedQuery = entityManager.createQuery(criteriaQuery);
        List<Object[]> list = typedQuery.getResultList();
        Assert.assertFalse(list.isEmpty());

        list.forEach(arr -> System.out.println(
                arr[0]
                        + ", size: " + arr[1]
        ));
    }

    @Test
    public void applyNumberFunction() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Object[]> criteriaQuery = criteriaBuilder.createQuery(Object[].class);
        Root<Order> root = criteriaQuery.from(Order.class);

        criteriaQuery.where(criteriaBuilder.greaterThan(criteriaBuilder.sqrt(root.get(Order_.total)), 10.0));

        criteriaQuery.multiselect(root.get(Order_.id),
                criteriaBuilder.abs(criteriaBuilder.prod(root.get(Order_.id), -1)),
                criteriaBuilder.mod(root.get(Order_.id), 2),
                criteriaBuilder.sqrt(root.get(Order_.total))
        );

        TypedQuery<Object[]> typedQuery = entityManager.createQuery(criteriaQuery);
        List<Object[]> list = typedQuery.getResultList();
        Assert.assertFalse(list.isEmpty());

        list.forEach(arr -> System.out.println(
                arr[0]
                        + ", abs: " + arr[1]
                        + ", mod: " + arr[2]
                        + ", sqrt: " + arr[3]
        ));
    }

    @Test
    public void applyDataFunction() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Object[]> criteriaQuery = criteriaBuilder.createQuery(Object[].class);
        Root<Order> root = criteriaQuery.from(Order.class);
        Join<Order, Payment> orderPaymentJoin = root.join(Order_.payment);
        Join<Order, SlipPayment> orderSlipPaymentJoin = criteriaBuilder.treat(orderPaymentJoin, SlipPayment.class);

        criteriaQuery.multiselect(root.get(Order_.id),
                criteriaBuilder.currentDate(),
                criteriaBuilder.currentTime(),
                criteriaBuilder.currentTimestamp()
        );

        criteriaQuery.where(
                criteriaBuilder.between(criteriaBuilder.currentDate(),
                        root.get(Order_.creationDate).as(java.sql.Date.class),
                        orderSlipPaymentJoin.get(SlipPayment_.dueDate).as(java.sql.Date.class)),
                criteriaBuilder.equal(root.get(Order_.status), OrderStatus.WAITING)
        );

        TypedQuery<Object[]> typedQuery = entityManager.createQuery(criteriaQuery);
        List<Object[]> list = typedQuery.getResultList();
        Assert.assertFalse(list.isEmpty());

        list.forEach(arr -> System.out.println(
                arr[0]
                        + ", current_date: " + arr[1]
                        + ", current_time: " + arr[2]
                        + ", current_timestamp: " + arr[3]
        ));
    }

    @Test
    public void applyStringFunction() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Object[]> criteriaQuery = criteriaBuilder.createQuery(Object[].class);
        Root<Client> root = criteriaQuery.from(Client.class);

        criteriaQuery.multiselect(root.get(Client_.name),
                criteriaBuilder.concat("Client's name: ", root.get(Client_.name)),
                criteriaBuilder.length(root.get(Client_.name)),
                criteriaBuilder.locate(root.get(Client_.name), "a"),
                criteriaBuilder.substring(root.get(Client_.name), 1, 3),
                criteriaBuilder.lower(root.get(Client_.name)),
                criteriaBuilder.upper(root.get(Client_.name)),
                criteriaBuilder.trim(root.get(Client_.name))
        );

        criteriaQuery.where(criteriaBuilder.equal(criteriaBuilder.substring(root.get(Client_.name), 1, 3), "a"));

        TypedQuery<Object[]> typedQuery = entityManager.createQuery(criteriaQuery);
        List<Object[]> list = typedQuery.getResultList();
        Assert.assertFalse(list.isEmpty());

        list.forEach(arr -> System.out.println(
                arr[0]
                        + ", concat: " + arr[1]
                        + ", length: " + arr[2]
                        + ", locate: " + arr[3]
                        + ", substring: " + arr[4]
                        + ", lower: " + arr[5]
                        + ", upper: " + arr[6]
                        + ", trim: " + arr[7]
        ));
    }
}
