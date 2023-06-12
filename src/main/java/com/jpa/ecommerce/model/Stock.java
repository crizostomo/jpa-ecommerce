package com.jpa.ecommerce.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "stock")
public class Stock extends IntegerBaseEntity {

    @NotNull
    @OneToOne
    @JoinColumn(name = "product_id")
    private Product product;

//    @Column(columnDefinition = "int(11)") // int for sql
    @NotNull
    @PositiveOrZero
    @Column(nullable = false)
    private Integer quantity;
}