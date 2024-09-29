package com.example.market.entity.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.math.BigDecimal;

@Data
@Entity
@Table(name = "products")
public class Product {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    @NotBlank(message = "Product name should not be empty")
    @Size(min = 3, message = "Product name should contain at least 2 characters")
    private String name;

    @Column(name = "price")
    @DecimalMin(value = "0.0", message = "Product price must be a decimal number with a minimal value of 0.0")
    private BigDecimal price;

    @ManyToOne
    @JoinColumn(name = "client_id", referencedColumnName = "id")
    private Client client;

}
