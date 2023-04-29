package com.jpa.ecommerce.jpql;

import com.jpa.ecommerce.EntityManagerTest;
import com.jpa.ecommerce.model.Client;
import com.jpa.ecommerce.model.Order;
import com.jpa.ecommerce.model.Product;
import jakarta.persistence.TypedQuery;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ConditionalExpressionsTest extends EntityManagerTest {

    @Test
    public void usingInExpressionWithClient() {
        Client client1 = entityManager.find(Client.class, 1);
//        Client client1 = new Client();
//        client1.setId(1);

        Client client2 = entityManager.find(Client.class, 2);
//        Client client2 = new Client();
//        client2.setId(2);

        List<Client> parameters = Arrays.asList(client1, client2);

        String jpql = "select o from Order o where o.client in (:clients)";

        TypedQuery<Order> typedQuery = entityManager.createQuery(jpql, Order.class);
        typedQuery.setParameter("clients", parameters);

        List<Order> list = typedQuery.getResultList();
        Assert.assertFalse(list.isEmpty());

        for (Order order : list) {
            System.out.println("Order ID: " + order.getId() + ", Date: " + order.getCreationDate());
        }
    }

    @Test
    public void usingInExpression() {
        List<Integer> parameters = Arrays.asList(1, 3, 4);

        String jpql = "select o from Order o where o.id in (:list)";

        TypedQuery<Product> typedQuery = entityManager.createQuery(jpql, Product.class);
        typedQuery.setParameter("list", parameters);

        List<Product> list = typedQuery.getResultList();
        Assert.assertFalse(list.isEmpty());
    }

    @Test
    public void usingCaseExpression() {
        String jpqlCase = "select o.id, " +
                "case o.status " +
                "when 'PAID' then 'IT IS PAID' " +
                "when 'CANCELLED' then 'IT IS CANCELLED' " +
                "else 'IT IS WAITING' " +
                "end " +
                "from Order o";

        String jpqlCaseAndType = "select o.id, " +
                "case type(o.payment) " +
                "when SlipPayment then 'Paid by Bank Slip' " +
                "when CardPayment then 'Paid by Card' " +
                "else 'It was not paid yet' " +
                "end " +
                "from Order o";

        TypedQuery<Object[]> typedQueryCase = entityManager.createQuery(jpqlCase, Object[].class);
        TypedQuery<Object[]> typedQueryCaseAndType = entityManager.createQuery(jpqlCaseAndType, Object[].class);

        List<Object[]> listCase = typedQueryCase.getResultList();
        List<Object[]> listCaseAndType = typedQueryCaseAndType.getResultList();

        Assert.assertFalse(listCase.isEmpty());
        Assert.assertFalse(listCaseAndType.isEmpty());

        listCase.forEach(arr -> System.out.println(arr[0] + ", " + arr[1]));
        listCaseAndType.forEach(arr -> System.out.println(arr[0] + ", " + arr[1]));
    }

    @Test
    public void usingDifferent() {
        String jpql = "select p from Product p where p.price <> :price";

        TypedQuery<Product> typedQuery = entityManager.createQuery(jpql, Product.class);
        typedQuery.setParameter("price", 100);

        List<Product> list = typedQuery.getResultList();
        Assert.assertFalse(list.isEmpty());
    }

    @Test
    public void useOfBetween() {
//        String jpql = "select p from Product p where p.price between :initialPrice and :finalPrice";
        String jpql = "select o from Order o where o.creationDate between :initialDate and :finalDate";

//        TypedQuery<Product> typedQuery = entityManager.createQuery(jpql, Product.class);
//        typedQuery.setParameter("initialPrice", new BigDecimal(400));
//        typedQuery.setParameter("finalPrice", new BigDecimal(1500));
        TypedQuery<Order> typedQuery = entityManager.createQuery(jpql, Order.class);
        typedQuery.setParameter("initialDate", LocalDateTime.now().minusDays(2));
        typedQuery.setParameter("finalDate", LocalDateTime.now());

//        List<Product> list = typedQuery.getResultList();
        List<Order> list = typedQuery.getResultList();
        Assert.assertFalse(list.isEmpty());
    }

    @Test
    public void greaterLesserWithDates() {
        String jpql = "select o from Order o where o.creationDate > :date";

        TypedQuery<Order> typedQuery = entityManager.createQuery(jpql, Order.class);
        typedQuery.setParameter("date", LocalDateTime.now().minusDays(2));

        List<Order> list = typedQuery.getResultList();
        Assert.assertFalse(list.isEmpty());
    }

    @Test
    public void greaterLesser() {
//        String jpql = "select p from Product p where p.price > :price";
        String jpql = "select p from Product p where p.price > :initialPrice and p.price <= :finalPrice";

        TypedQuery<Product> typedQuery = entityManager.createQuery(jpql, Product.class);
//        typedQuery.setParameter("price", new BigDecimal(500));
        typedQuery.setParameter("initialPrice", new BigDecimal(400));
        typedQuery.setParameter("finalPrice", new BigDecimal(1500));

        List<Product> list = typedQuery.getResultList();
        Assert.assertFalse(list.isEmpty());
    }

    @Test
    public void isNull() {
        String jpql = "select p from Product p where p.photo is null";
//        String jpql = "select p from Product p where p.photo is not null";

        TypedQuery<Object[]> typedQuery = entityManager.createQuery(jpql, Object[].class);

        List<Object[]> list = typedQuery.getResultList();
        Assert.assertFalse(list.isEmpty());
    }

    @Test
    public void isEmpty() {
        String jpql = "select p from Product p where p.categories is empty";

        TypedQuery<Object[]> typedQuery = entityManager.createQuery(jpql, Object[].class);

        List<Object[]> list = typedQuery.getResultList();
        Assert.assertFalse(list.isEmpty());
    }

    @Test
    public void useConditionalExpressionLike() {
        String jpql = "select c from Client c where c.name like concat(:name, '%')";
//        String jpql = "select c from Client c where c.name like concat('%', :name, '%')"; // You want to search the middle
//        String jpql = "select c from Client c where c.name like concat('%', :name)"; // If you want what it is at the end

        TypedQuery<Object[]> typedQuery = entityManager.createQuery(jpql, Object[].class);
        typedQuery.setParameter("name", "Roronoa");

        List<Object[]> list = typedQuery.getResultList();
        Assert.assertFalse(list.isEmpty());
    }
}
