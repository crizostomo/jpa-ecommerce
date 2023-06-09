package com.jpa.ecommerce.nativeQuerySearchTest;

import com.jpa.ecommerce.EntityManagerTest;
import com.jpa.ecommerce.model.Client;
import jakarta.persistence.Query;
import org.junit.Test;

import java.util.List;

public class ViewTest extends EntityManagerTest {

    @Test
    public void executeView() {
        Query query = entityManager.createNativeQuery(
                "select cli.id, cli.name, sum(ord.total) " +
                        " from `order` ord " +
                        " join view_clients_above_average cli on cli.id = ord.client_id " +
                        " group by ord.client_id");

        List<Object[]> list = query.getResultList();

        list.stream().forEach(arr -> System.out.println(
                String.format("Client => ID: %s, Name: %s, Total: %s", arr)));
    }

    @Test
    public void executeViewReturningClient() {
        Query query = entityManager.createNativeQuery(
                "select * from view_clients_above_average", Client.class);

        List<Client> list = query.getResultList();

        list.stream().forEach(obj -> System.out.println(
                String.format("Client => ID: %s, Name: %s", obj.getId(), obj.getName())));
    }
}