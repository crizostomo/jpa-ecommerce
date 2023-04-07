package com.jpa.ecommerce.model;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter
@Setter
public class DeliveryAddress {

    private String zipCode;

    private String street;

    private String number;

    private String complement;

    private String neighborhood;

    private String city;

    private String state;
}
