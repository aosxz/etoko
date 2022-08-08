package com.etoko.etoko.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.LinkedHashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Product {
    @Id
    @Column(name = "ProductID", nullable = false, length = 20)
    private String productId;

    @Column(name = "NameProduct", nullable = false, length = 50)
    private String nameProduct;

    @Column(name = "Category", nullable = false, length = 50)
    private String category;

    @Column(name = "Price", nullable = false, precision = 19, scale = 4)
    private BigDecimal price;

    @Lob
    @Column(name = "Description")
    private String description;

    @Column(name = "Stock")
    private Integer stock;

    @OneToMany(mappedBy = "productId")
    private Set<Purchase> purchases = new LinkedHashSet<>();

    public Product(String productId, String nameProduct, String category, BigDecimal price, String description, Integer stock) {
        this.productId = productId;
        this.nameProduct = nameProduct;
        this.category = category;
        this.price = price;
        this.description = description;
        this.stock = stock;
    }
}