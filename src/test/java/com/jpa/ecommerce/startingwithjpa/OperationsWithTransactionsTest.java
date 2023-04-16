package com.jpa.ecommerce.startingwithjpa;

import com.jpa.ecommerce.EntityManagerTest;
import com.jpa.ecommerce.model.Product;
import org.junit.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class OperationsWithTransactionsTest extends EntityManagerTest {

    @Test
    public void avoidOperationWithDB() {
        Product product = entityManager.find(Product.class, 1);
        entityManager.detach(product);

        entityManager.getTransaction().begin();
        product.setName("Kindle Paperwhite 2ª Generation");
        entityManager.getTransaction().commit();

        entityManager.clear();

        Product productVerification = entityManager.find(Product.class, product.getId());
        Assert.assertEquals("Kindle Paperwhite 2ª Generation", productVerification.getName());
    }

    @Test
    public void showDifferenceWithPersistMerge() {
        Product persistProduct = new Product();

//        persistProduct.setId(5);
        persistProduct.setName("Smartphone One Plus");
        persistProduct.setDescription("O processador mais rápido.");
        persistProduct.setPrice(new BigDecimal(2000));
        persistProduct.setCreationDate(LocalDateTime.now());

        entityManager.getTransaction().begin();
        entityManager.persist(persistProduct);
        persistProduct.setName("Smartphone Two Plus");
        entityManager.getTransaction().commit();

        entityManager.clear();

        Product persistProductVerification = entityManager.find(Product.class, persistProduct.getId());
        Assert.assertNotNull(persistProductVerification);

        Product mergeProduct = new Product();

//        mergeProduct.setId(6);
        mergeProduct.setName("Dell Laptop");
        mergeProduct.setDescription("The best one");
        mergeProduct.setPrice(new BigDecimal(2000));
        mergeProduct.setCreationDate(LocalDateTime.now());

        entityManager.getTransaction().begin();
        mergeProduct = entityManager.merge(mergeProduct);
        mergeProduct.setName("Dell Laptop 2");
        entityManager.getTransaction().commit();

        entityManager.clear();

        Product mergeProductVerification = entityManager.find(Product.class, mergeProduct.getId());
        Assert.assertNotNull(mergeProductVerification);
    }

    @Test
    public void insertObjectWithMerge() {
        Product product = new Product();

        product.setId(4);
        product.setName("Mic Rode Videmic");
        product.setDescription("The best quality");
        product.setPrice(new BigDecimal(1000));
        product.setCreationDate(LocalDateTime.now());

        entityManager.getTransaction().begin();
        entityManager.merge(product);
        entityManager.getTransaction().commit();

        entityManager.clear();

        Product productVerification = entityManager.find(Product.class, product.getId());
        Assert.assertNotNull(productVerification);
    }

    @Test
    public void updateManagedObject() {
        Product product = entityManager.find(Product.class, 1);

        entityManager.getTransaction().begin();
        product.setName("Kindle Paperwhite 2ª Generation");
        entityManager.getTransaction().commit();

        entityManager.clear();

        Product productVerification = entityManager.find(Product.class, product.getId());
        Assert.assertEquals("Kindle Paperwhite 2ª Generation", productVerification.getName());
    }

    @Test
    public void updateObject() {
        Product product = new Product();

        product.setId(1);
        product.setName("Kindle Paperwhite");
        product.setDescription("Know more about the new Kindle.");
        product.setPrice(new BigDecimal(599));
        product.setCreationDate(LocalDateTime.now());

        entityManager.getTransaction().begin();
        entityManager.merge(product);
        entityManager.getTransaction().commit();

        entityManager.clear();

        Product productVerification = entityManager.find(Product.class, product.getId());
        Assert.assertNotNull(productVerification);
        Assert.assertEquals("Kindle Paperwhite", productVerification.getName());
    }

    @Test
    public void removeObject() {
        Product product = entityManager.find(Product.class, 3);

        entityManager.getTransaction().begin();
        entityManager.remove(product);
        entityManager.getTransaction().commit();

//        entityManager.clear(); There is no need in the assertion for operation removal.

        Product productVerification = entityManager.find(Product.class, 3);
        Assert.assertNull(productVerification);
    }

    @Test
    public void insertTheFirstObject() {
        Product product = new Product();

//        product.setId(2);
        product.setName("Canon Camera");
        product.setDescription("The best definition for your photos");
        product.setPrice(new BigDecimal(5000));
        product.setCreationDate(LocalDateTime.now());

        entityManager.getTransaction().begin();
        entityManager.persist(product);
        entityManager.getTransaction().commit();

        entityManager.clear();

        Product productVerification = entityManager.find(Product.class, product.getId());
        Assert.assertNotNull(productVerification);
    }

    @Test
    public void openAndCloseTransaction() {
//        Product product = new Product(); // Just to avoid to show errors.

        entityManager.getTransaction().begin();

//        entityManager.persist(product);
//        entityManager.merge(product);
//        entityManager.remove(product);

        entityManager.getTransaction().commit();
    }
}