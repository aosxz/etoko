package com.etoko.etoko.services.abstraction;

import com.etoko.etoko.dtos.buyer.BuyerGridDTO;
import com.etoko.etoko.dtos.buyer.UpsertBuyerDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface BuyerService {

    public Page<BuyerGridDTO> findAllBuyer(Integer page);
    public List<BuyerGridDTO>findBuyerById(Integer buyerId);
    public List<BuyerGridDTO>insertBuyer(UpsertBuyerDTO newBuyer);
    public Boolean updateBuyer(Integer buyerId,UpsertBuyerDTO buyerDTO);
    public Boolean deleteBuyer(Integer buyerId);


}
