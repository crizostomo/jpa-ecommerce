package com.jpa.ecommerce.advancedmapping;

import com.jpa.ecommerce.EntityManagerTest;
import com.jpa.ecommerce.model.Invoice;
import com.jpa.ecommerce.model.Order;
import org.junit.Assert;
import org.junit.Test;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;

public class SavingFilesTest extends EntityManagerTest {

    @Test
    public void saveXmlInvoice() {
        Order order = entityManager.find(Order.class, 1);

        Invoice invoice = new Invoice();
        invoice.setOrder(order);
        invoice.setIssuingDate(new Date());
        invoice.setXml(loadInvoice());

        entityManager.getTransaction().begin();
        entityManager.persist(invoice);
        entityManager.getTransaction().commit();

        Invoice invoiceVerification = entityManager.find(Invoice.class, invoice.getId());
        Assert.assertNotNull(invoiceVerification.getXml());
        Assert.assertTrue(invoiceVerification.getXml().length > 0);

//        /*
        try {
            OutputStream out = new FileOutputStream(
                    Files.createFile(Paths.get(
                            System.getProperty("user.home") + "/xml.xml")).toFile());
            out.write(invoiceVerification.getXml());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
//        */
    }

    private static byte[] loadInvoice() {
        try {
            return SavingFilesTest.class.getResourceAsStream(
                    "/invoice.xml").readAllBytes();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
