package com.laioffer.onlineorder.entity;


import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

// @Table tells SpringData JDBC that this entity class is connected to table called "customers"
@Table("customers")

// Corresopnds to customer table in DB
public record CustomerEntity(
        // Tells SpringData JDBC that this corresponds to primary key
        @Id Long id,
        String email,
        String password,
        boolean enabled,
        String firstName,
        String lastName
) {
}
