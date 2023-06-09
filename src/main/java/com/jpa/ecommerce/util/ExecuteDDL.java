package com.jpa.ecommerce.util;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.HashMap;
import java.util.Map;

public class ExecuteDDL {

    //To execute this class, you must comment in the persistence.xml what is between the two <!--**-->
    public static void main(String[] args) {
        Map<String, String> properties = new HashMap<>();

        properties.put("jakarta.persistence.jdbc.url", "jdbc:mysql://localhost/jpa_store_ecommerce?createDatabaseIfNotExist=true&useTimezone=true&serverTimezone=UTC");

        properties.put("jakarta.persistence.schema-generation.database.action",
                "drop-and-create");

        properties.put("jakarta.persistence.schema-generation.create-source",
                "metadata-then-script");

        properties.put("jakarta.persistence.schema-generation.drop-source",
                "metadata-then-script");

        properties.put("jakarta.persistence.schema-generation.create-script-source",
                "META-INF/database/script-create.sql");

        properties.put("jakarta.persistence.schema-generation.drop-script-source",
                "META-INF/database/script-remove.sql");

        properties.put("jakarta.persistence.sql-load-script-source",
                "META-INF/database/initial-data.sql");

        EntityManagerFactory entityManagerFactory = Persistence
                .createEntityManagerFactory("Ecommerce-PU", properties);

        entityManagerFactory.close();
    }
}