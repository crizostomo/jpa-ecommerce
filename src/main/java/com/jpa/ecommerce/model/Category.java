package com.jpa.ecommerce.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "category",
        uniqueConstraints = {@UniqueConstraint(name = "unq_name", columnNames = {"name"})})
public class Category extends IntegerBaseEntity {

    private String name;

    @ManyToOne
    @JoinColumn(name = "father_category_id")
    private Category fatherCategory;

    @OneToMany(mappedBy = "fatherCategory")
    private List<Category> categories;

    @ManyToMany(mappedBy = "categories")
    private List<Product> products;
}