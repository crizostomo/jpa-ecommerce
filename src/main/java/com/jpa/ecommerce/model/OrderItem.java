package com.jpa.ecommerce.model;

import jakarta.persistence.*;
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

    @EmbeddedId
    private OrderItemId id;

    @MapsId("orderId")
//    @ManyToOne(optional = false, cascade = CascadeType.MERGE) // For the class CascadeTypePersistTest
//    @ManyToOne(optional = false, cascade = CascadeType.REMOVE) // For the class CascadeTypeRemoveTest
    @ManyToOne(optional = false) // For the class CascadeTypeRemoveTest
    @JoinColumn(name = "order_id", foreignKey = @ForeignKey(name = "fk_order_item_order"))
    private Order order;

    @MapsId("productId")
    @ManyToOne
    @JoinColumn(name = "product_id", foreignKey = @ForeignKey(name = "fk_order_item_product"))
    private Product product;

    @Column(name = "product_price")
    private BigDecimal productPrice;

    private Integer quantity;
}