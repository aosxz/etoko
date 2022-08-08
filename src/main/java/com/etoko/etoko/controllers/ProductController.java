package com.etoko.etoko.controllers;


import com.etoko.etoko.dtos.RestResponse;
import com.etoko.etoko.dtos.buyer.BuyerGridDTO;
import com.etoko.etoko.dtos.buyer.UpsertBuyerDTO;
import com.etoko.etoko.dtos.product.ProductGridDTO;
import com.etoko.etoko.dtos.product.UpsertProductDTO;
import com.etoko.etoko.services.abstraction.ProductService;
import com.etoko.etoko.services.implementation.RegexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private RegexService regexService;

    @GetMapping("get")
    public ResponseEntity<RestResponse<Page<ProductGridDTO>>> findAll(Integer page) {
        return new ResponseEntity<>(
                new RestResponse<>(productService.findAllProduct(page),
                        "Product Berhasil ditemukan",
                        200),
                HttpStatus.OK);
    }


    @GetMapping("find-by-id/{id}")
    public ResponseEntity<RestResponse<List<ProductGridDTO>>> findProductById(String id) {
        return new ResponseEntity<>(
                new RestResponse<>(productService.findProductById(id),
                        "Product id " + id + " berhasil ditemukan",
                        200),
                HttpStatus.OK
        );

    }

    @PostMapping("insert")
    public ResponseEntity<RestResponse<Object>> insertProduct(@RequestBody UpsertProductDTO newProduct) {
        Object[] error = {"Product Id :"+ newProduct.getProductId() + " sudah ada sebelum nya"};

        if (productService.checkExixtingProductId(newProduct.getProductId()) == true) {
           return new ResponseEntity<>(
                   new RestResponse<>(error,
                           "Product id " + newProduct.getProductId() + " sudah ada",
                           400),
                   HttpStatus.BAD_REQUEST
           );
       }

        if (regexService.detectCharacterSpecial(newProduct.getProductId()) == false
        && regexService.detectCharacterSpecial(newProduct.getNameProduct()) == false
        && regexService.detectCharacterSpecial(newProduct.getCategory()) == false
                && regexService.detectCharacterSpecial(newProduct.getDescription()) == false
        ) {
            return new ResponseEntity<>(
                    new RestResponse<>(productService.insertProduct(newProduct),
                            "Product berhasil ditambahkan",
                            200),
                    HttpStatus.OK);
        } else {
            Object[] args = {"Inputan : tidak boleh mengandung karakter spesial"};

            return new ResponseEntity<>(
                    new RestResponse<>(args,
                            "Product gagal ditambahkan karena mengandung karakter special",
                            400),
                    HttpStatus.BAD_REQUEST);
        }

    }


    @PutMapping("update/{productId}")
    public ResponseEntity<RestResponse> deleteBuyer(@PathVariable String productId, @RequestBody UpsertProductDTO upsertProduct) {
        return new ResponseEntity(
                new RestResponse(productService.updateProduct(productId, upsertProduct),
                        "Product dengan "+ productId + " berhasil di Update",
                        200),
                HttpStatus.OK);
    }

    @DeleteMapping("delete/{buyerId}")
    public ResponseEntity<RestResponse> deleteBuyer(@PathVariable String productId) {
        return new ResponseEntity(
                new RestResponse(productService.deleteProduct(productId),
                        "Product dengan " + productId+ " berhasil dihapus",
                        200),
                HttpStatus.OK);
    }


}
