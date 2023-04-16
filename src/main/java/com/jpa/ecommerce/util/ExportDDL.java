package com.jpa.ecommerce.util;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.HashMap;
import java.util.Map;

public class ExportDDL {
    
    //To execute this class, you must comment in the persistence.xml what is between the two <!--**-->
    public static void main(String[] args) {
        Map<String, String> properties = new HashMap<>();

        properties.put("javax.persistence.schema-generation.scripts.action",
                "drop-and-create");
        properties.put("javax.persistence.schema-generation.scripts.create-target",
                "C:/tmp/sql/script-create-exported.sql");
        properties.put("javax.persistence.schema-generation.scripts.drop-target",
                "C:/tmp/sql/script-remove-exported.sql");

        properties.put("javax.persistence.schema-generation.create-source",
                "metadata-then-script");
        properties.put("javax.persistence.schema-generation.drop-source",
                "metadata-then-script");

        properties.put("javax.persistence.schema-generation.create-script-source",
                "META-INF/database/script-create.sql");
        properties.put("javax.persistence.schema-generation.drop-script-source",
                "META-INF/database/script-remove.sql");

        properties.put("javax.persistence.sql-load-script-source",
                "META-INF/database/initial-data.sql");

        EntityManagerFactory entityManagerFactory = Persistence
                .createEntityManagerFactory("Ecommerce-PU", properties);

        entityManagerFactory.close();
    }
}