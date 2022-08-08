package com.etoko.etoko.dtos.purchase;

import com.etoko.etoko.entity.Buyer;
import com.etoko.etoko.entity.Product;
import com.etoko.etoko.entity.Purchase;
import com.etoko.etoko.entity.Shipment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpsertPurchaseDTO {



    @NotBlank(message = "Purchase ID tidak boleh kosong")
    private String purchaseId;
    private  Integer buyerId;
    private  String nameShipment;
    private  String productId;
    private LocalDate purchaseDate;
    private  Integer quantity;



    public Purchase convertPurchase(Buyer buyer, Shipment shipment, Product product){
        return new Purchase(
                purchaseId,
                buyer,
                shipment,
                product,
                LocalDate.now(),
                quantity);
    }
}
