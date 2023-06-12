package com.jpa.ecommerce.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@DiscriminatorValue("card")
//@Table(name = "card_payment")
public class CardPayment extends Payment {

    @NotEmpty
    @Column(name = "card_number", length = 50)
    private String cardNumber;
}