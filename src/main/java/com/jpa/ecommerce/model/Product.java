package com.jpa.ecommerce.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "product",
        uniqueConstraints = {@UniqueConstraint(name = "unq_name", columnNames = {"name"})},
        indexes = { @Index(name = "idx_name", columnList = "name")})
public class Product extends IntegerBaseEntity {

    @Column(length = 100, nullable = false)
    private String name;

    @Column(columnDefinition = "varchar(275) not null default 'description'")
    private String description;

    @Column(precision = 19, scale = 2) // 19 digits including the last 2
    private BigDecimal price;

    @Column(name = "creation_date")
    private LocalDateTime creationDate;

    @Column(name = "last_update_date")
    private LocalDateTime lastUpdateDate;

    @OneToMany(mappedBy = "product")
    private List<OrderItem> orderItems;

    @ManyToMany
    @JoinTable(name = "product_category",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id"))
    private List<Category> categories;

    @OneToMany(mappedBy = "product")
    private Stock stock;

    @ElementCollection
    @CollectionTable(name = "product_tag",
            joinColumns = @JoinColumn(name = "product_id"))
    @Column(name = "tag")
    private List<String> tags;

    @ElementCollection
    @CollectionTable(name = "product_attribute",
            joinColumns = @JoinColumn(name = "product_id"))
    private List<Attribute> attributes;

    @Lob // Lob = Large Object
    private byte[] photo;
}
