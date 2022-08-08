package com.etoko.etoko.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Purchase {
    @Id
    @Column(name = "PurchaseID", nullable = false, length = 20)
    private String purchaseId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "BuyerID", nullable = false)
    private Buyer buyerId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ShipmentName", nullable = false)
    private Shipment nameShipment;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ProductID", nullable = false)
    private Product productId;

    @Column(name = "PurchaseDate", nullable = false)
    private LocalDate purchaseDate;

    @Column(name = "Quantity", nullable = false)
    private Integer quantity;


    public Purchase( Buyer buyerId, Shipment nameShipment, Product productId, LocalDate purchaseDate, Integer quantity) {
        this.buyerId = buyerId;
        this.nameShipment = nameShipment;
        this.productId = productId;
        this.purchaseDate = purchaseDate;
        this.quantity = quantity;
    }
}