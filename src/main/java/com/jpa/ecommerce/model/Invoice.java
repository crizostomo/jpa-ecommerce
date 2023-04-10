package com.jpa.ecommerce.model;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "invoice")
public class Invoice {

    @EqualsAndHashCode.Include
    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Integer id;

    @MapsId
    @OneToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @Lob // Lob = Large Object
    private byte[] xml;

    @Column(name = "issuing_date")
    private Date issuingDate;
}