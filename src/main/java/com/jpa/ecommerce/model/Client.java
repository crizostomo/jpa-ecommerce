package com.jpa.ecommerce.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@Entity
@SecondaryTable(name = "client_detail",
        pkJoinColumns = @PrimaryKeyJoinColumn(name = "client_id"),
        foreignKey = @ForeignKey(name = "fk_client_detail_client"))
@Table(name = "client",
        uniqueConstraints = {@UniqueConstraint(name = "unq_cpf", columnNames = {"cpf"})},
        indexes = { @Index(name = "idx_name", columnList = "name")})
public class Client extends IntegerBaseEntity {

    private String name;

    private String cpf;

    @ElementCollection
    @CollectionTable(name = "client_contact",
            joinColumns = @JoinColumn(name = "client_id",
            foreignKey = @ForeignKey(name = "fk_client_contact_client")))
    @MapKeyColumn(name = "type")
    @Column(name = "description")
    private Map<String, String> contact;

    @Transient
    private String firstName;

    @Column(table = "client_detail")
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(name = "birth_date", table = "client_detail")
    private LocalDate birthDate;

    @OneToMany(mappedBy = "client")
    private List<Order> orders;

    @PostLoad
    public void showFirstName() {
        if (name != null && !name.isBlank()) {
            int index = name.indexOf(" ");
            if (index > -1) {
                firstName = name.substring(0, index);
            }
        }
    }
}