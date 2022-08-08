package com.etoko.etoko.dtos.product;


import com.etoko.etoko.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductGridDTO {

    private  String productId;
    private  String nameProduct;
    private  String category;
    private  String price;
    private  String description;
    private  Integer stock;

    public static ProductGridDTO set(Product product){
        //create format currency
        Locale locale = new Locale("id","ID");
        NumberFormat formatDuit = NumberFormat.getCurrencyInstance(locale);
        return new ProductGridDTO(
                product.getProductId(),
                product.getNameProduct(),
                product.getCategory(),
                formatDuit.format(product.getPrice()),
                product.getDescription(),
                product.getStock());
    }
    public static List<ProductGridDTO> toList(List<Product> products){
        List<ProductGridDTO> result = new ArrayList<>();
        for (Product product :products){
            result.add(set(product));
        }
        return result;
    }
}
