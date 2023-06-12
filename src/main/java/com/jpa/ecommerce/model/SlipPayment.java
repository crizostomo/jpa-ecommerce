package com.jpa.ecommerce.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@DiscriminatorValue("slip")
//@Table(name = "slip_payment")
public class SlipPayment extends Payment {

    @NotBlank
    @Column(name = "barcode", length = 100)
    private String barcode;

    @NotNull
    @FutureOrPresent
    @Column(name = "due_date")
    private LocalDate dueDate;
}