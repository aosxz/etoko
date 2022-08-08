package com.etoko.etoko.dtos.purchase;

import com.etoko.etoko.entity.Purchase;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseGridDTO {

    private  String purchaseId;
    private  Integer buyerId;
    private  String nameShipment;
    private  String productId;
    private  String purchaseDate;
    private  Integer quantity;
    private  String totalPrice;



    public static PurchaseGridDTO set(Purchase purchase) {
        DateTimeFormatter formatTanggal = DateTimeFormatter.ofPattern("dd MMMM yyyy", new Locale("id","ID"));
        Locale locale = new Locale("id","ID");
        NumberFormat formatCurrency = NumberFormat.getCurrencyInstance(locale);
        return new PurchaseGridDTO(
                purchase.getPurchaseId(),
                purchase.getBuyerId().getBuyerId(),
                purchase.getNameShipment().getNameShipment(),
                purchase.getProductId().getProductId(),
                formatTanggal.format(purchase.getPurchaseDate()),
                purchase.getQuantity(),
//                formatCurrency.format(purchase.getProductId().getPrice())
//                formatCurrency.getSymbol() + purchase.getProductId().getPrice()
//               purchase.getProductId().getPrice()
                formatCurrency.format(purchase.getProductId().getPrice().multiply(new BigDecimal(purchase.getQuantity())).add(purchase.getNameShipment().getCost())));
//                formatCurrency.format(purchase.getProductId().getPrice())


    }

    public static List<PurchaseGridDTO> toList(List<Purchase> purchases){
        List<PurchaseGridDTO> result = new ArrayList<>();
        for (Purchase purchase :purchases){
            result.add(set(purchase));
        }
        return result;

    }


}
