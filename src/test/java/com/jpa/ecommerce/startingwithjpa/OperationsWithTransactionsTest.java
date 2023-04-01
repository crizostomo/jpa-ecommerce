package com.jpa.ecommerce.startingwithjpa;

import com.jpa.ecommerce.EntityManagerTest;
import com.jpa.ecommerce.model.Product;
import org.junit.*;

import java.math.BigDecimal;

public class OperationsWithTransactionsTest extends EntityManagerTest {

    @Test
    public void avoidOperationWithDB() {
        Product product = entityManager.find(Product.class, 1);
        entityManager.detach(product);

        entityManager.getTransaction().begin();
        product.setName("Kindle Paperwhite 2ª Geração");
        entityManager.getTransaction().commit();

        entityManager.clear();

        Product verifyProduct = entityManager.find(Product.class, product.getId());
        Assert.assertEquals("Kindle", verifyProduct.getName());
    }

    @Test
    public void showDifferenceWithPersistMerge() {
        Product persistProduct = new Product();

        persistProduct.setId(5);
        persistProduct.setName("Smartphone One Plus");
        persistProduct.setDescription("O processador mais rápido.");
        persistProduct.setPrice(new BigDecimal(2000));

        entityManager.getTransaction().begin();
        entityManager.persist(persistProduct);
        persistProduct.setName("Smartphone Two Plus");
        entityManager.getTransaction().commit();

        entityManager.clear();

        Product persistProductVerification = entityManager.find(Product.class, persistProduct.getId());
        Assert.assertNotNull(persistProductVerification);

        Product mergeProduct = new Product();

        mergeProduct.setId(6);
        mergeProduct.setName("Notebook Dell");
        mergeProduct.setDescription("O melhor da categoria.");
        mergeProduct.setPrice(new BigDecimal(2000));

        entityManager.getTransaction().begin();
        mergeProduct = entityManager.merge(mergeProduct);
        mergeProduct.setName("Notebook Dell 2");
        entityManager.getTransaction().commit();

        entityManager.clear();

        Product mergeProductVerification = entityManager.find(Product.class, mergeProduct.getId());
        Assert.assertNotNull(mergeProductVerification);
    }

    @Test
    public void insertObjectWithMerge() {
        Product product = new Product();

        product.setId(4);
        product.setName("Microfone Rode Videmic");
        product.setDescription("A melhor qualidade de som.");
        product.setPrice(new BigDecimal(1000));

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
        product.setName("Kindle Paperwhite 2ª Geração");
        entityManager.getTransaction().commit();

        entityManager.clear();

        Product productVerification = entityManager.find(Product.class, product.getId());
        Assert.assertEquals("Kindle Paperwhite 2ª Geração", productVerification.getName());
    }

    @Test
    public void updateObject() {
        Product product = new Product();

        product.setId(1);
        product.setName("Kindle Paperwhite");
        product.setDescription("Conheça o novo Kindle.");
        product.setPrice(new BigDecimal(599));

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

        product.setId(2);
        product.setName("Câmera Canon");
        product.setDescription("A melhor definição para suas fotos.");
        product.setPrice(new BigDecimal(5000));

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