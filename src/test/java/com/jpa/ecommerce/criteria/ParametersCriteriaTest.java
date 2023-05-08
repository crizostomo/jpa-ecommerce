package com.jpa.ecommerce.criteria;

import com.jpa.ecommerce.EntityManagerTest;
import com.jpa.ecommerce.model.Invoice;
import com.jpa.ecommerce.model.Order;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.ParameterExpression;
import jakarta.persistence.criteria.Root;
import org.junit.Assert;
import org.junit.Test;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ParametersCriteriaTest extends EntityManagerTest {

    @Test
    public void inputDateParameters() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Invoice> criteriaQuery = criteriaBuilder.createQuery(Invoice.class);
        Root<Invoice> root = criteriaQuery.from(Invoice.class);

        criteriaQuery.select(root);

        ParameterExpression<Date> parameterExpressionDate = criteriaBuilder.parameter(Date.class, "initialDate");

        criteriaQuery.where(criteriaBuilder.greaterThan(root.get("issuingDate"), parameterExpressionDate));

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -30);
        Date initialDate = calendar.getTime();

        TypedQuery<Invoice> typedQuery = entityManager.createQuery(criteriaQuery);
        typedQuery.setParameter("initialDate", initialDate);

        List<Invoice> invoiceList = typedQuery.getResultList();
        Assert.assertFalse(invoiceList.isEmpty());
    }

    @Test
    public void inputParameters() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Order> criteriaQuery = criteriaBuilder.createQuery(Order.class);
        Root<Order> root = criteriaQuery.from(Order.class);

        criteriaQuery.select(root);

        ParameterExpression<Integer> parameterExpressionId = criteriaBuilder.parameter(Integer.class, "id");

        criteriaQuery.where(criteriaBuilder.equal(root.get("id"), parameterExpressionId));

        TypedQuery<Order> typedQuery = entityManager.createQuery(criteriaQuery);
        typedQuery.setParameter("id", 1);

        Order order = typedQuery.getSingleResult();
        Assert.assertNotNull(order);

        List<Order> list = typedQuery.getResultList();
        Assert.assertFalse(list.isEmpty());
    }
}
