package com.jpa.ecommerce.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Order {

    @EqualsAndHashCode.Include
    @Id
    private Integer id;

    private LocalDateTime orderDate;

    private LocalDateTime endDate;

    private Integer invoiceId;

    private BigDecimal total;

    private OrderStatus status;
}