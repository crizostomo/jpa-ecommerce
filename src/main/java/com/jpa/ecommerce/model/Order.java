package com.jpa.ecommerce.model;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "order")
public class Order {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "client_id") // We do not need since the attribute client will join the attribute id
    private Client client;

    @Column(name = "order_date")
    private LocalDateTime orderDate;

    @Column(name = "end_date")
    private LocalDateTime endDate;

    @Column(name = "invoice_id")
    private Integer invoiceId;

    private BigDecimal total;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @Embedded
    private DeliveryAddress deliveryAddress;

    @OneToMany(mappedBy = "order")
    private List<OrderItem> orderItems;

    @OneToOne(mappedBy = "order")
    private CardPayment payment;

    @OneToOne(mappedBy = "order")
    private Invoice invoice;
}