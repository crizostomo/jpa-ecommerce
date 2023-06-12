package com.jpa.ecommerce.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter
@Setter
public class DeliveryAddress {

    @NotNull
    @Pattern(regexp = "[0-9]{5}-[0-9]{3}")
    @Column(length = 9)
    private String zipCode;

    @NotBlank
    @Column(length = 100)
    private String street;

    @NotBlank
    @Column(length = 10)
    private String number;

    @Column(length = 100)
    private String complement;

    @NotBlank
    @Column(length = 50)
    private String neighborhood;

    @NotBlank
    @Column(length = 50)
    private String city;

    @NotBlank
    @Size(max = 2, min = 2)
    @Column(length = 2)
    private String state;
}
