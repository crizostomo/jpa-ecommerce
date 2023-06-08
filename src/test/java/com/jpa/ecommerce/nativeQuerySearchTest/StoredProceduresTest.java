package com.jpa.ecommerce.nativeQuerySearchTest;

import com.jpa.ecommerce.EntityManagerTest;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.StoredProcedureQuery;
import org.junit.Assert;
import org.junit.Test;

public class StoredProceduresTest extends EntityManagerTest {

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
