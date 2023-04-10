package com.jpa.ecommerce.model;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@DiscriminatorValue("card")
//@Table(name = "card_payment")
public class CardPayment extends Payment {

    @Column(name = "card_number")
    private String cardNumber;
}