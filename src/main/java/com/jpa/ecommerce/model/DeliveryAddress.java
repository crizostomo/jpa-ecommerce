package com.jpa.ecommerce.model;

import jakarta.persistence.Embeddable;

@Embeddable
public class DeliveryAddress {

    private String zipCode;

    private String street;

    private String number;

    private String complement;

    private String neighborhood;

    private String city;

    private String state;
}
