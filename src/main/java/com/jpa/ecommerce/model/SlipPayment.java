package com.jpa.ecommerce.model;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@DiscriminatorValue("slip")
@Table(name = "slip_payment")
public class SlipPayment extends Payment {

    private String barcode;
}