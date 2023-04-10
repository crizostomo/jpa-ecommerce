package com.jpa.ecommerce.model;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "slip_payment")
public class SlipPayment extends IntegerBaseEntity {

    @Column(name = "order_id")
    private Integer orderId;

    @Enumerated(EnumType.STRING)
    private PaymentStatus status;

    private String barcode;
}