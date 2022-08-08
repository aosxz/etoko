package com.etoko.etoko.controllers;


import com.etoko.etoko.dtos.RestResponse;
import com.etoko.etoko.dtos.purchase.PurchaseGridDTO;
import com.etoko.etoko.dtos.purchase.UpsertPurchaseDTO;
import com.etoko.etoko.services.abstraction.PurchaseService;
import com.etoko.etoko.services.implementation.RegexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/purchase")
public class PurchaseController {

    @Autowired
    private PurchaseService purchaseService;

    @Autowired
    private RegexService regexService;

    @GetMapping("getAll")
    public ResponseEntity<RestResponse<Page<PurchaseGridDTO>>> getAllPurchase(Integer page) {
        return new ResponseEntity<>(
                new RestResponse<>(purchaseService.findAllPurchase(page),
                        "Purchase berhasil ditemukan",
                        200),
                HttpStatus.OK);
    }

    @GetMapping("findByPurchaseDate/{purchaseDate}")
    public ResponseEntity<RestResponse<List<PurchaseGridDTO>>> findByPurchaseDate(@PathVariable String purchaseDate) {
        return new ResponseEntity<>(
                new RestResponse<>(purchaseService.findPurchaseByDate(purchaseDate),
                        "Purchase dengan tanggal" + purchaseDate + " berhasil ditemukan",
                        200),
                HttpStatus.OK);
    }

    @GetMapping("findPurchaseByBuyerId/{buyerId}")
    public ResponseEntity<RestResponse<List<PurchaseGridDTO>>> findPurchaseByBuyerId(@PathVariable Integer buyerId) {
        return new ResponseEntity<>(
                new RestResponse<>(purchaseService.findPurchaseByBuyerId(buyerId),
                        "Purchase dengan buyer ID " + buyerId + " berhasil ditemukan",
                        200),
                HttpStatus.OK);
    }

    @GetMapping("findPurchaseByPurchaseId/{purchaseId}")
    public ResponseEntity<RestResponse<PurchaseGridDTO>> findPurchaseByPurchaseId(@PathVariable String purchaseId) {
        return new ResponseEntity(
                new RestResponse<>(purchaseService.findPurchaseById(purchaseId),
                        "Purchase dengan ID " + purchaseId + " berhasil ditemukan",
                        200),
                HttpStatus.OK);
    }

    @GetMapping("findPurchaseByShipment/{nameShipment}")
    public ResponseEntity<RestResponse<List<PurchaseGridDTO>>> findPurchaseByShipment(@PathVariable String nameShipment) {
        return new ResponseEntity<>(
                new RestResponse<>(purchaseService.findPurchaseByShipment(nameShipment),
                        "Purchase dengan shipment " + nameShipment + " berhasil ditemukan",
                        200),
                HttpStatus.OK);
    }

    @GetMapping("findPurchaseByCategoryProduct/{category}")
    public ResponseEntity<RestResponse<List<PurchaseGridDTO>>> findPurchaseByCategoryProduct(@PathVariable String category) {
        return new ResponseEntity<>(
                new RestResponse<>(purchaseService.findPurchaseByCategoryProduct(category),
                        "Purchase dengan category " + category + " berhasil ditemukan",
                        200),
                HttpStatus.OK);
    }


    @PostMapping("insert")
    public ResponseEntity<RestResponse<Object>> insertPurchase( @RequestBody UpsertPurchaseDTO newPurchase ) {
        Object[] noInput = {"Purchase Id :"+ newPurchase.getPurchaseId() + " Tidak boleh Kosong"};
        if (newPurchase.getPurchaseId().equals("")) {
            return new ResponseEntity<>(
                    new RestResponse<>(  noInput,
                            "Product Id Tidak boleh kosong",
                            400),
                    HttpStatus.BAD_REQUEST);
        }

        Object[] error = {"Purchase Id :"+ newPurchase.getPurchaseId() + " sudah ada sebelum nya"};

        if (purchaseService.checkExixtingPurchaseId(newPurchase.getPurchaseId()) == true) {
            return new ResponseEntity<>(
                    new RestResponse<>(error,
                            "Purchase id " + newPurchase.getPurchaseId() + " sudah ada",
                            400),
                    HttpStatus.BAD_REQUEST
            );
        }

            if (regexService.detectCharacterSpecial(newPurchase.getPurchaseId()) == false &&
                    regexService.detectCharacterSpecial(newPurchase.getProductId()) == false
            ) {
                return new ResponseEntity<>(
                        new RestResponse<>(purchaseService.insertPurchase(newPurchase),
                                "Purchase berhasil ditambahkan",
                                200),
                        HttpStatus.OK);
            } else {
                Object[] args = {"Inputan : tidak boleh mengandung karakter spesial"};

                return new ResponseEntity<>(
                        new RestResponse<>(args,
                                "Purchase gagal ditambahkan karena mengandung karakter special",
                                400),
                        HttpStatus.BAD_REQUEST);
            }

        }


    @PutMapping("update/{purchaseId}")
    public ResponseEntity<RestResponse<PurchaseGridDTO>> updatePurchase(@PathVariable String purchaseId, @RequestBody UpsertPurchaseDTO newPurchase) {
        return new ResponseEntity(
                new RestResponse<>(purchaseService.updatePurchase(purchaseId, newPurchase),
                        "Purchase " + purchaseId + " berhasil diubah",
                        200),
                HttpStatus.OK);
    }

    @DeleteMapping("delete/{purchaseId}")
    public ResponseEntity<RestResponse<PurchaseGridDTO>> deletePurchase(@PathVariable String purchaseId) {
        return new ResponseEntity(
                new RestResponse<>(purchaseService.deletePurchase(purchaseId),
                        "Purchase " + purchaseId + " berhasil dihapus",
                        200),
                HttpStatus.OK);
    }


}
