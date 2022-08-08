package com.etoko.etoko.controllers;

import com.etoko.etoko.dtos.RestResponse;
import com.etoko.etoko.dtos.buyer.BuyerGridDTO;
import com.etoko.etoko.dtos.buyer.UpsertBuyerDTO;
import com.etoko.etoko.services.abstraction.BuyerService;
import com.etoko.etoko.services.implementation.RegexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/buyer")
public class BuyerController {

    @Autowired
    private BuyerService buyerService;

    @Autowired
    private RegexService regexService;


    @GetMapping("get")
    public ResponseEntity<RestResponse<Page<BuyerGridDTO>>> findAll(Integer page) {
        return new ResponseEntity<>(
                new RestResponse<>(buyerService.findAllBuyer(page),
                        "Buyer Berhasil ditemukan",
                        200),
                HttpStatus.OK);
    }


    @GetMapping("find-by-id/{id}")
    public ResponseEntity<RestResponse<List<BuyerGridDTO>>> findBuyerById(Integer id) {
        return new ResponseEntity<>(
                new RestResponse<>(buyerService.findBuyerById(id),
                        "Buyer id " + id + " berhasil ditemukan",
                        200),
                HttpStatus.OK
        );

    }

    @PostMapping("insert")
    public ResponseEntity<RestResponse<Object>> insertBuyer(@RequestBody UpsertBuyerDTO newBuyer) {
        if (regexService.detectCharacterSpecial(newBuyer.getFirstName()) == false
                && regexService.detectCharacterSpecial(newBuyer.getLastName()) == false
                && regexService.detectCharacterSpecial(newBuyer.getBirthPlace()) == false
        ) {
            return new ResponseEntity(
                    new RestResponse<>(buyerService.insertBuyer(newBuyer),
                            "Buyer berhasil ditambahkan",
                            201),
                    HttpStatus.CREATED);
        } else {
            Object[] error = {"Inputan : tidak boleh mengandung karakter spesial"};
            return new ResponseEntity<>(
                    new RestResponse<>(error,
                            "Mengandung karakter spesial",
                            400),
                    HttpStatus.BAD_REQUEST
            );
        }
    }

    @PutMapping("update/{buyerId}")
    public ResponseEntity<RestResponse> deleteBuyer(@PathVariable Integer buyerId, @RequestBody UpsertBuyerDTO upsertBuyer) {
        if (regexService.detectCharacterSpecial(upsertBuyer.getFirstName()) == false
                && regexService.detectCharacterSpecial(upsertBuyer.getLastName()) == false
            && regexService.detectCharacterSpecial(upsertBuyer.getBirthPlace()) == false
        ) {
            return new ResponseEntity(
                    new RestResponse<>(buyerService.updateBuyer(buyerId, upsertBuyer),
                            "Buyer id " + buyerId + " berhasil di Update",
                            201),
                    HttpStatus.CREATED);
        }else {
            Object[] args = {"Inputan : tidak boleh mengandung karakter spesial"};

            return new ResponseEntity<>(
                    new RestResponse<>(args,
                            "Buyer id " + buyerId + " gagal di Update karena mengandung karakter special",
                            400),
                    HttpStatus.BAD_REQUEST);
        }



    }


    @DeleteMapping("delete/{buyerId}")
    public ResponseEntity<RestResponse> deleteBuyer(@PathVariable Integer buyerId) {
        return new ResponseEntity(
                new RestResponse(buyerService.deleteBuyer(buyerId),
                        "Buyer dengan " + buyerId+ " berhasil dihapus",
                        200),
                HttpStatus.OK);
    }


}
