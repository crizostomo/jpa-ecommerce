package com.jpa.ecommerce.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Id;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class OrderItemId implements Serializable {

    @EqualsAndHashCode.Include
    @Column(name = "order_id")
    private Integer orderId;

    @EqualsAndHashCode.Include
    @Column(name = "product_id")
    private Integer productId;
}
