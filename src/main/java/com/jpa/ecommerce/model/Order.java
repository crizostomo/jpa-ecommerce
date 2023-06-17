package com.jpa.ecommerce.model;

import com.jpa.ecommerce.listener.CreateInvoiceListener;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;
import org.hibernate.engine.spi.PersistentAttributeInterceptable;
import org.hibernate.engine.spi.PersistentAttributeInterceptor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NamedEntityGraphs({ // Equivalent to configuring in the EntityGraphTest
        @NamedEntityGraph(
                name = "Order.essentialData",
                attributeNodes = {
                        @NamedAttributeNode("creationDate"),
                        @NamedAttributeNode("status"),
                        @NamedAttributeNode("total"),
                        @NamedAttributeNode(
                                value = "client",
                                subgraph = "cli"
                        )
                },
                subgraphs = {
                        @NamedSubgraph(
                                name = "cli",
                                attributeNodes = {
                                        @NamedAttributeNode("name"),
                                        @NamedAttributeNode("cpf")
                                }
                        )
                }
        )
})
@Entity
@EntityListeners({CreateInvoiceListener.class})
@Table(name = "`order`")
public class Order extends IntegerBaseEntity
//        implements PersistentAttributeInterceptable
{

    @NotNull
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
    @NotEmpty
    private List<OrderItem> orderItems;

    @PastOrPresent
    @NotNull
    @Column(name = "creation_date")
    private LocalDateTime creationDate;

    @PastOrPresent
    @Column(name = "last_update_date")
    private LocalDateTime lastUpdateDate;

    @Column(name = "ending_date")
    private LocalDateTime endingDate;

//    @LazyToOne(LazyToOneOption.NO_PROXY)
    @OneToOne(mappedBy = "order"
//            , fetch = FetchType.LAZY
    )
    private Invoice invoice;

    @NotNull
    @Positive
    @Column(nullable = false)
    private BigDecimal total;

    @NotNull
    @Column(length = 30, nullable = false)
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

//    @LazyToOne(LazyToOneOption.NO_PROXY)
    @OneToOne(mappedBy = "order"
//            , fetch = FetchType.LAZY
    )
    private Payment payment;

    @Embedded
    private DeliveryAddress deliveryAddress;

    /**
     * Added the methods getInvoice, setInvoice, getPayment and setPayment just to show a feature about the test class
     * "OneOneLazyTest"
     * @return
     */
//    public Invoice getInvoice() {
//        if (this.persistentAttributeInterceptor != null) {
//            return (Invoice) persistentAttributeInterceptor.readObject(this, "invoice", this.invoice);
//        }
//        return this.invoice;
//    }
//
//    public void setInvoice(Invoice invoice) {
//        if (this.persistentAttributeInterceptor != null) {
//            this.invoice = (Invoice) persistentAttributeInterceptor.writeObject(this, "invoice", this.invoice, invoice);
//        } else {
//            this.invoice = invoice;
//        }
//    }
//
//    public Payment getPayment() {
//        if (this.persistentAttributeInterceptor != null) {
//            return (Payment) persistentAttributeInterceptor.readObject(this, "payment", this.payment);
//        }
//        return this.payment;
//    }
//
//    public void setPayment(Payment payment) {
//        if (this.persistentAttributeInterceptor != null) {
//            this.payment = (Payment) persistentAttributeInterceptor.writeObject(this, "payment", this.payment, payment);
//        } else {
//            this.payment = payment;
//        }
//    }
//
//    @Transient
//    @Setter(AccessLevel.NONE)
//    @Getter(AccessLevel.NONE)
//    private PersistentAttributeInterceptor persistentAttributeInterceptor;
//
//    @Override
//    public PersistentAttributeInterceptor $$_hibernate_getInterceptor() {
//        return this.persistentAttributeInterceptor;
//    }
//
//    @Override
//    public void $$_hibernate_setInterceptor(PersistentAttributeInterceptor persistentAttributeInterceptor) {
//        this.persistentAttributeInterceptor = persistentAttributeInterceptor;
//    }

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