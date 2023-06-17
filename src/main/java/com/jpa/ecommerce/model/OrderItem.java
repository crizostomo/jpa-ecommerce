package com.jpa.ecommerce.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@SqlResultSetMappings({
        @SqlResultSetMapping(name = "order_item_product.OrderItem-Product", entities = {
                @EntityResult(entityClass = OrderItem.class), @EntityResult(entityClass = Product.class)
        })
})
@Entity
@Table(name = "order_item")
public class OrderItem {

    @Version
    private Integer version;

    @EmbeddedId
    private OrderItemId id;

    @NotNull
    @MapsId("orderId")
//    @ManyToOne(optional = false, cascade = CascadeType.MERGE) // For the class CascadeTypePersistTest
//    @ManyToOne(optional = false, cascade = CascadeType.REMOVE) // For the class CascadeTypeRemoveTest
    @ManyToOne(optional = false) // For the class CascadeTypeRemoveTest
    @JoinColumn(name = "order_id", foreignKey = @ForeignKey(name = "fk_order_item_order"))
    private Order order;

    @NotNull
    @MapsId("productId")
    @ManyToOne
    @JoinColumn(name = "product_id", foreignKey = @ForeignKey(name = "fk_order_item_product"))
    private Product product;

    @Positive
    @NotNull
    @Column(name = "product_price", nullable = false)
    private BigDecimal productPrice;

    @Positive
    @NotNull
    @Column(nullable = false)
    private Integer quantity;
}