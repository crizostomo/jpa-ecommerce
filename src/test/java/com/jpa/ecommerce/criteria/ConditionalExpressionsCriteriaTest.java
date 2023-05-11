package com.jpa.ecommerce.criteria;

import com.jpa.ecommerce.EntityManagerTest;
import com.jpa.ecommerce.model.Client;
import com.jpa.ecommerce.model.Client_;
import com.jpa.ecommerce.model.Product;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class ConditionalExpressionsCriteriaTest extends EntityManagerTest {


    @Test  // Similar to the exercise in the Class ConditionalExpressionsTest - useConditionalExpressionLike
    public void conditionalExpressionLike() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Client> criteriaQuery = criteriaBuilder.createQuery(Client.class);
        Root<Client> root = criteriaQuery.from(Client.class);

        criteriaQuery.select(root);

        criteriaQuery.where(criteriaBuilder.like(root.get(Client_.name), "%o%")); //A% - It starts with 'A' | %A - It finishes with 'A' | %A% - It contains 'A'

        TypedQuery<Client> typedQuery = entityManager.createQuery(criteriaQuery);

        List<Client> clientList = typedQuery.getResultList();
        Assert.assertFalse(clientList.isEmpty());
    }
}
