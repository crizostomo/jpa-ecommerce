package com.jpa.ecommerce.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@NamedQueries({
        @NamedQuery(name = "Product.list", query = "select p from Product p"),
        @NamedQuery(name = "Product.listByCategories", query = "select p from Product p " +
                "where exists (select 1 from Category c2 " +
                "join c2.products p2 where p2 = p " +
                "and c2. id = :category)"),
})
@Entity
@Getter
@Setter
@SqlResultSetMappings({
        @SqlResultSetMapping(name = "product_store.Product", entities = {
                @EntityResult(entityClass = Product.class)
        }),
        @SqlResultSetMapping(name = "product_ecm.Product", entities = {
                @EntityResult(entityClass = Product.class,
                fields = {
                        @FieldResult(name = "id", column = "prd_id"),
                        @FieldResult(name = "name", column = "prd_name"),
                        @FieldResult(name = "description", column = "prd_description"),
                        @FieldResult(name = "price", column = "prd_price"),
                        @FieldResult(name = "photo", column = "prd_photo"),
                        @FieldResult(name = "creationDate", column = "prd_creation_date"),
                        @FieldResult(name = "lastUpdateDate", column = "prd_last_update_date")
                })
        })
})
@Table(name = "product",
        uniqueConstraints = {@UniqueConstraint(name = "unq_name", columnNames = {"name"})},
        indexes = {@Index(name = "idx_name", columnList = "name")})
public class Product extends IntegerBaseEntity {

    @Column(length = 100, nullable = false)
    private String name;

    //    @Column(columnDefinition = "varchar(275) not null default 'description'")
    @Lob // To input long text
    private String description;

    @Column(precision = 19, scale = 2) // 19 digits including the last 2
    private BigDecimal price;

    @Column(name = "creation_date")
    private LocalDateTime creationDate;

    @Column(name = "last_update_date")
    private LocalDateTime lastUpdateDate;

    @OneToMany(mappedBy = "product")
    private List<OrderItem> orderItems;

//    @ManyToMany(cascade = CascadeType.MERGE) // For the class CascadeTypePersistTest
    @ManyToMany
    @JoinTable(name = "product_category",
            joinColumns = @JoinColumn(name = "product_id", nullable = false,
                    foreignKey = @ForeignKey(name = "fk_category_product_product")),
            inverseJoinColumns = @JoinColumn(name = "category_id", nullable = false,
                    foreignKey = @ForeignKey(name = "fk_category_product_category")))
    private List<Category> categories;

    @OneToOne(mappedBy = "product")
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

    @Column(columnDefinition = "varchar(12000)")
    @Lob // Lob = Large Object
    private byte[] photo;
}
