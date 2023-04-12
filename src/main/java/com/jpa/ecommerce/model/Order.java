package com.jpa.ecommerce.model;

import com.jpa.ecommerce.listener.CreateInvoiceListener;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
@EntityListeners({CreateInvoiceListener.class})
@Table(name = "order")
public class Order extends IntegerBaseEntity {

    @ManyToOne
    @JoinColumn(name = "client_id") // We do not need since the attribute client will join the attribute id
    private Client client;

    @Column(name = "creation_date")
    private LocalDateTime creationDate;

    @Column(name = "last_update_date")
    private LocalDateTime lastUpdateDate;

    @Column(name = "ending_date")
    private LocalDateTime endingDate;

    @Column(name = "invoice_id")
    private Integer invoiceId;

    private BigDecimal total;

    @Column(length = 30, nullable = false)
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

    @PrePersist
    public void onPersisting() {
        creationDate = LocalDateTime.now();
        calculateTotal();
    }

    @PreUpdate
    public void onUpdating() {
        lastUpdateDate = LocalDateTime.now();
        calculateTotal();
    }

    public void calculateTotal() {
        if (orderItems != null) {
            total = orderItems.stream().map(OrderItem::getProductPrice)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
        }
    }

    public boolean isPaid() {
        return OrderStatus.PAID.equals(status);
    }
}