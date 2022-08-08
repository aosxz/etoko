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
public class Shipment {
    @Id
    @Column(name = "Name", nullable = false, length = 50)
    private String nameShipment;

    @Lob
    @Column(name = "Description")
    private String description;

    @Column(name = "Cost", nullable = false, precision = 19, scale = 4)
    private BigDecimal cost;

    @OneToMany(mappedBy = "nameShipment")
    private Set<Purchase> purchases = new LinkedHashSet<>();


    public Shipment(String nameShipment, String description, BigDecimal cost) {
        this.nameShipment = nameShipment;
        this.description = description;
        this.cost = cost;
    }
}