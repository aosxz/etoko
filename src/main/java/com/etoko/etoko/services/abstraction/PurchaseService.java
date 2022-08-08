package com.etoko.etoko.services.abstraction;

import com.etoko.etoko.dtos.purchase.PurchaseGridDTO;
import com.etoko.etoko.dtos.purchase.UpsertPurchaseDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface PurchaseService {


    public Page<PurchaseGridDTO> findAllPurchase(Integer page);

    public List<PurchaseGridDTO>findPurchaseById(String purchaseId);

    public PurchaseGridDTO insertPurchase(UpsertPurchaseDTO newPurchase);

    public List<PurchaseGridDTO> findPurchaseByDate(String purchaseDate);

    public List<PurchaseGridDTO> findPurchaseByBuyerId(Integer buyerId);

    public List<PurchaseGridDTO> findPurchaseByShipment(String nameShipment);

    public List<PurchaseGridDTO> findPurchaseByCategoryProduct(String category);

    public boolean updatePurchase(String purchaseId, UpsertPurchaseDTO upsertPurchaseDTO);

    public boolean deletePurchase(String purchaseId);

    public Boolean checkExixtingPurchaseId(String purchaseId);

}
