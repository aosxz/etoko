package com.etoko.etoko.services.implementation;


import com.etoko.etoko.dao.BuyerRepository;
import com.etoko.etoko.dao.ProductRepository;
import com.etoko.etoko.dao.PurchaseRepository;
import com.etoko.etoko.dao.ShipmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class RegexService {

    @Autowired
    private BuyerRepository buyerRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private PurchaseRepository purchaseRepository;

    @Autowired
    private ShipmentRepository shipmentRepository;


    public Boolean detectCharacterSpecial(String caracter) {
        Pattern pattern = Pattern.compile("[!@#$%^&*()_+ \\-=\\[\\]{};':,.<>/?]");
        Matcher matcher = pattern.matcher(caracter);
        var check = matcher.find();
        return check;
    }

}
