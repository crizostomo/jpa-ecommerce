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
@Table(name = "`order`")
public class Order extends IntegerBaseEntity {

    @ManyToOne(optional = false)
    @JoinColumn(name = "client_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_order_client")) // We do not need since the attribute client will join the attribute id
    private Client client;

//    @OneToMany(mappedBy = "order", cascade = CascadeType.MERGE) // For the class CascadeTypePersistTest
//    @OneToMany(mappedBy = "order", cascade = CascadeType.REMOVE) // For the class CascadeTypeRemoveTest
//    @OneToMany(mappedBy = "order",
//            cascade = CascadeType.PERSIST,
//            orphanRemoval = true) // For OneToOne/OneToMany --> Equivalent to cascade.Remove and needs cascade method to be used | // For the class CascadeTypeRemoveTest
    @OneToMany(mappedBy = "order")
    private List<OrderItem> orderItems;

    @Column(name = "creation_date")
    private LocalDateTime creationDate;

    @Column(name = "last_update_date")
    private LocalDateTime lastUpdateDate;

    @Column(name = "ending_date")
    private LocalDateTime endingDate;

    @OneToOne(mappedBy = "order")
    private Invoice invoice;

    private BigDecimal total;

    @Column(length = 30, nullable = false)
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @OneToOne(mappedBy = "order")
    private Payment payment;

    @Embedded
    private DeliveryAddress deliveryAddress;

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
            total = orderItems.stream().map(
                    i -> new BigDecimal(i.getQuantity()).multiply(i.getProductPrice()))
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
        } else {
            total = BigDecimal.ZERO;
        }
    }

    @PostPersist
    public void afterPersisting() {
        System.out.println("After persisting product");
    }

    public boolean isPaid() {
        return OrderStatus.PAID.equals(status);
    }
}