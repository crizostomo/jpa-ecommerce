package com.jpa.ecommerce.model;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "stock")
public class Stock extends IntegerBaseEntity {

    @OneToOne
    @JoinColumn(name = "product_id")
    private Product product;

    private Integer quantity;
}