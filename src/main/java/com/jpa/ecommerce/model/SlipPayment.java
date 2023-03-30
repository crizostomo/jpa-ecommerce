package com.jpa.ecommerce.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class SlipPayment {

    @EqualsAndHashCode.Include
    @Id
    private Integer id;

    private Integer orderId;

    private PaymentStatus status;

    private String barcode;
}