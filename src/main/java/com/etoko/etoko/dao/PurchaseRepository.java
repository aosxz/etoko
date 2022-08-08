package com.etoko.etoko.dao;

import com.etoko.etoko.entity.Purchase;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PurchaseRepository extends JpaRepository<Purchase, String> {



    @Query(value = "select * from purchase where PurchaseDate = :purchaseDate", nativeQuery = true)
    List<Purchase> getPurchaseByDate(@Param("purchaseDate") String purchaseDate);


    @Query(value = """
            select  p.PurchaseID,p.BuyerID,p.ShipmentName,p.ProductID,p.PurchaseDate,p.Quantity 
            from Purchase p 
            join Buyer b on b.BuyerID = p.BuyerID
            where b.BuyerID = :buyerId
                           
            """, nativeQuery = true)
    List<Purchase> getPurchaseByBuyerId(@Param("buyerId") Integer buyerId);


    @Query(value = """
            select  p.PurchaseID,p.BuyerID,p.ShipmentName,p.ProductID,p.PurchaseDate,p.Quantity 
            from Purchase p 
            where p.ShipmentName = :nameShipment
                           
            """, nativeQuery = true)
    List<Purchase> getPurchaseByShipment(@Param("nameShipment") String nameShipment);


    @Query(value = """
            select  p.PurchaseID,p.BuyerID,p.ShipmentName,p.ProductID,p.PurchaseDate,p.Quantity
            from Purchase p
            join Product pr on pr.ProductID= p.ProductID
            where pr.Category = :category
                           
            """, nativeQuery = true)
    List<Purchase> getPurchaseByCategoryProduct(@Param("category") String category);

}