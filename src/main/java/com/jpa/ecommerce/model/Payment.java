package com.jpa.ecommerce.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
//@DiscriminatorColumn(name = "payment_type")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
//@Table(name = "payment")
public abstract class Payment extends IntegerBaseEntity {

    @MapsId
    @OneToOne(optional = false)
    @JoinColumn(name = "order_id")
    private Order order;

    @Enumerated(EnumType.STRING)
    private PaymentStatus status;
}
