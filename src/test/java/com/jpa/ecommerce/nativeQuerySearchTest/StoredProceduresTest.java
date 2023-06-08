package com.jpa.ecommerce.nativeQuerySearchTest;

import com.jpa.ecommerce.EntityManagerTest;
import com.jpa.ecommerce.model.Client;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.StoredProcedureQuery;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class StoredProceduresTest extends EntityManagerTest {

    @Test
    public void receiveProcedureList() {
        StoredProcedureQuery storedProcedureQuery = entityManager.createStoredProcedureQuery("bought_above_average", Client.class);

        storedProcedureQuery.registerStoredProcedureParameter("ano", Integer.class, ParameterMode.IN);

        storedProcedureQuery.setParameter("ano", 2023);

        List<Client> list = storedProcedureQuery.getResultList();

        Assert.assertFalse(list.isEmpty());
    }

    @Test
    public void useInAndOutParameters() {
        StoredProcedureQuery storedProcedureQuery = entityManager.createStoredProcedureQuery("search_product_name");

        storedProcedureQuery.registerStoredProcedureParameter("product_id", Integer.class, ParameterMode.IN);

        storedProcedureQuery.registerStoredProcedureParameter("product_name", String.class, ParameterMode.OUT);

        storedProcedureQuery.setParameter("product_id", 1);

        String name = (String) storedProcedureQuery.getOutputParameterValue("product_name");

        Assert.assertEquals("Kindle", name);
    }
}
