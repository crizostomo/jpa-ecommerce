package com.jpa.ecommerce.model;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "invoice")
public class Invoice extends IntegerBaseEntity {

    @MapsId
    @OneToOne
    @JoinColumn(name = "order_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_invoice_order"))
    private Order order;

    @Lob // Lob = Large Object
    private byte[] xml;

    @Column(name = "issuing_date")
    private Date issuingDate;
}