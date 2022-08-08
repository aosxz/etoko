package com.etoko.etoko.services.implementation;

import com.etoko.etoko.dao.BuyerRepository;
import com.etoko.etoko.dao.ProductRepository;
import com.etoko.etoko.dao.PurchaseRepository;
import com.etoko.etoko.dao.ShipmentRepository;
import com.etoko.etoko.dtos.RestResponse;
import com.etoko.etoko.dtos.purchase.PurchaseGridDTO;
import com.etoko.etoko.dtos.purchase.UpsertPurchaseDTO;
import com.etoko.etoko.entity.Purchase;
import com.etoko.etoko.services.abstraction.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class PurchaseImplementation implements PurchaseService {
    @Autowired
    private PurchaseRepository purchaseRepository;

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private BuyerRepository buyerRepository;
    @Autowired
    private ShipmentRepository shipmentRepository;


    @Override
    public Page<PurchaseGridDTO> findAllPurchase(Integer page) {
        var purchaseList = purchaseRepository.findAll();
        var pagination = PageRequest.of(page - 1, 10);
        Locale locale = new Locale("id", "ID");
        NumberFormat formatDuit = NumberFormat.getCurrencyInstance(locale);
        DateTimeFormatter formatTanggal = DateTimeFormatter.ofPattern("dd MMMM yyyy", new Locale("id", "ID"));
        var pageObject = purchaseList.stream().skip(pagination.getOffset()).limit(pagination.getPageSize()).map(purchase -> {
            return new PurchaseGridDTO(
                     purchase.getPurchaseId(),
                     purchase.getBuyerId().getBuyerId(),
                     purchase.getNameShipment().getNameShipment(),
                     purchase.getProductId().getProductId(),
                     formatTanggal.format(purchase.getPurchaseDate()),
                     purchase.getQuantity(),
                     formatDuit.format(purchase.getProductId().getPrice().multiply(new BigDecimal(purchase.getQuantity())).add(purchase.getNameShipment().getCost())));
        }).collect(Collectors.toList());
        if (pageObject.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Data not found");
        }
        return new PageImpl<>(pageObject, pagination, purchaseList.size());

    }

    @Override
    public List<PurchaseGridDTO> findPurchaseById(String purchaseId) {
        Optional<Purchase> purchaseList = Optional.ofNullable(purchaseRepository.findById(purchaseId).orElseThrow(() -> new EntityNotFoundException("Data tidak ditemukan")));
        Locale locale = new Locale("id", "ID");
        NumberFormat formatDuit = NumberFormat.getCurrencyInstance(locale);
        DateTimeFormatter formatTanggal = DateTimeFormatter.ofPattern("dd MMMM yyyy", new Locale("id", "ID"));
        List<PurchaseGridDTO> purchaseStream = purchaseList.stream()
                .map(purchase -> new PurchaseGridDTO(
                        purchase.getPurchaseId(),
                        purchase.getBuyerId().getBuyerId(),
                        purchase.getNameShipment().getNameShipment(),
                        purchase.getProductId().getProductId(),
                        formatTanggal.format(purchase.getPurchaseDate()),
                        purchase.getQuantity(),
                        formatDuit.format(purchase.getProductId().getPrice().multiply(new BigDecimal(purchase.getQuantity())).add(purchase.getNameShipment().getCost())))
                ).collect(Collectors.toList());
        return purchaseStream;
    }


    @Override
    public PurchaseGridDTO insertPurchase(UpsertPurchaseDTO newPurchase) {
        var buyer = buyerRepository.findById(newPurchase.getBuyerId()).
                orElseThrow(() -> new EntityNotFoundException("Buyer Tidak Ditemukan"));

        var product = productRepository.findById(newPurchase.getProductId())
                .orElseThrow(() -> new EntityNotFoundException("Product Tidak Ditemukan"));

        var shipment = shipmentRepository.findById(newPurchase.getNameShipment()).
                orElseThrow(() -> new EntityNotFoundException("Shimpent Tidak Ditemukan"));

        if (newPurchase.getQuantity() > product.getStock()) {
            throw new EntityNotFoundException("Quantity Melebihi Stock");
        }
        Purchase purchase = newPurchase.convertPurchase(buyer, shipment, product);
        product.setStock(product.getStock() - newPurchase.getQuantity());
        purchaseRepository.save(purchase);
        productRepository.save(product);


        return PurchaseGridDTO.set(purchase);

    }

    @Override
    public List<PurchaseGridDTO> findPurchaseByDate(String purchaseDate) {
        LocalDate localDate = LocalDate.parse(purchaseDate, DateTimeFormatter.ofPattern("dd-MMMM-yyyy"));
        purchaseRepository.getPurchaseByDate(localDate.toString());
        List<Purchase> purchaseList = purchaseRepository.getPurchaseByDate(localDate.toString());
        if (purchaseList.isEmpty()) {
            throw new EntityNotFoundException("Purchase Date Tidak Ditemukan");
        }

        return PurchaseGridDTO.toList(purchaseRepository.getPurchaseByDate(purchaseDate));
    }

    @Override
    public List<PurchaseGridDTO> findPurchaseByBuyerId(Integer buyerId) {
        List<Purchase> filterBuyerId = purchaseRepository.getPurchaseByBuyerId(buyerId);
        if (filterBuyerId.isEmpty()) {
            throw new EntityNotFoundException("Purchase dengan BuyerID " + buyerId + " Tidak Ditemukan");
        }
        purchaseRepository.getPurchaseByBuyerId(buyerId);
        return PurchaseGridDTO.toList(purchaseRepository.getPurchaseByBuyerId(buyerId));

    }

    @Override
    public List<PurchaseGridDTO> findPurchaseByShipment(String nameShipment) {
        List<Purchase> filterPurchaseByShipment = purchaseRepository.getPurchaseByShipment(nameShipment);
        if (filterPurchaseByShipment.isEmpty()) {
            throw new EntityNotFoundException("Purchase dengan Shipment " + nameShipment + " Tidak Ditemukan");
        }
        purchaseRepository.getPurchaseByShipment(nameShipment);
        return PurchaseGridDTO.toList(purchaseRepository.getPurchaseByShipment(nameShipment));
    }

    @Override
    public List<PurchaseGridDTO> findPurchaseByCategoryProduct(String category) {
        List<Purchase> filterPurchaseByCategoryProduct = purchaseRepository.getPurchaseByCategoryProduct(category);
        if (filterPurchaseByCategoryProduct.isEmpty()) {
            throw new EntityNotFoundException("Purchase dengan Category Product " + category + " Tidak Ditemukan");
        }
        purchaseRepository.getPurchaseByCategoryProduct(category);
        return PurchaseGridDTO.toList(purchaseRepository.getPurchaseByCategoryProduct(category));
    }

    @Override
    public boolean updatePurchase(String purchaseId, UpsertPurchaseDTO upsertPurchaseDTO) {
        var oldPurchase = purchaseRepository.findById(purchaseId)
                .orElseThrow(() -> new EntityNotFoundException("Purchase Tidak Ditemukan"));
        var product = productRepository.findById(upsertPurchaseDTO.getProductId())
                .orElseThrow(() -> new EntityNotFoundException("Product Tidak Ditemukan"));


        oldPurchase.setQuantity(upsertPurchaseDTO.getQuantity() == null ? oldPurchase.getQuantity() : upsertPurchaseDTO.getQuantity());
        product.setStock(product.getStock() - upsertPurchaseDTO.getQuantity());
        purchaseRepository.save(oldPurchase);
        productRepository.save(product);

        return true;

    }

    @Override
    public boolean deletePurchase(String purchaseId) {
        purchaseRepository.findById(purchaseId)
                .orElseThrow((() -> new EntityNotFoundException("Purchase dengan Purchase ID " + purchaseId + " tidak ditemukan")));
        purchaseRepository.deleteById(purchaseId);
        return true;
    }

    @Override
    public Boolean checkExixtingPurchaseId(String purchaseId) {
        var check = purchaseRepository.findById(purchaseId).isPresent();
        return check;
    }


}
