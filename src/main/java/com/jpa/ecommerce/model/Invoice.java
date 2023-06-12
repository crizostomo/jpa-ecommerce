package com.jpa.ecommerce.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "invoice")
public class Invoice extends IntegerBaseEntity {

    @NotNull
    @MapsId
    @OneToOne
    @JoinColumn(name = "order_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_invoice_order"))
    private Order order;

    @NotEmpty
    @Column(nullable = false)
    @Lob // Lob = Large Object
    private byte[] xml;

    @PastOrPresent
    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "issuing_date", nullable = false)
    private Date issuingDate;
}