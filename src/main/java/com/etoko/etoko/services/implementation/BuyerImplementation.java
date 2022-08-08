package com.etoko.etoko.services.implementation;

import com.etoko.etoko.dao.BuyerRepository;
import com.etoko.etoko.dtos.buyer.BuyerGridDTO;
import com.etoko.etoko.dtos.buyer.UpsertBuyerDTO;
import com.etoko.etoko.entity.Buyer;
import com.etoko.etoko.services.abstraction.BuyerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;


@Service
public class BuyerImplementation implements BuyerService {

    @Autowired
    private BuyerRepository buyerRepository;

    @Override
    public Page<BuyerGridDTO> findAllBuyer(Integer page) {
        var pagination  = PageRequest.of(page - 1, 10);
        var buyerList= buyerRepository.findAll();
        DateTimeFormatter formatTanggal = DateTimeFormatter.ofPattern("dd MMMM yyyy",new Locale("id","ID"));
        var buyerStream = buyerList.stream().skip(pagination.getOffset()).limit(pagination.getPageSize()).map(buyer -> {
                    return new BuyerGridDTO(
                            buyer.getBuyerId(),
                            buyer.getFirstName(),
                            buyer.getLastName(),
                            formatTanggal.format(buyer.getBirthDate()),
                            buyer.getBirthPlace(),
                            buyer.getAddress()
                    );
                }
        ).collect(Collectors.toList());
        if (buyerStream.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Data not found");
        }
        return new PageImpl<>(buyerStream, pagination, buyerList.size());
    }

    @Override
    public List<BuyerGridDTO> findBuyerById(Integer buyerId) {
        var  buyerList = buyerRepository.findById(buyerId);
        buyerRepository.findById(buyerId).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Data tidak ditemukan"));
         var buyerStream = buyerList.stream().
                map(buyer -> new BuyerGridDTO(
                        buyer.getBuyerId(),
                        buyer.getFirstName(),
                        buyer.getLastName(),
                        buyer.getBirthDate().toString(),
                        buyer.getBirthPlace(),
                        buyer.getAddress()
                )).collect(Collectors.toList());

        return buyerStream;
    }

    @Override
    public List<BuyerGridDTO> insertBuyer(UpsertBuyerDTO newBuyer) {
        Buyer buyer = newBuyer.convert();
        DateTimeFormatter formatTanggal = DateTimeFormatter.ofPattern("dd MMMM yyyy", new Locale("id","ID"));
        LocalDate tanggalLahir = LocalDate.parse(newBuyer.getBirthDate(), formatTanggal);
        if (tanggalLahir.isEqual(LocalDate.now()) || tanggalLahir.isAfter(LocalDate.now())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Tanggal lahir tidak boleh lebih besar dari tanggal sekarang");
        }

        buyerRepository.save(buyer);
        return  BuyerGridDTO.toList(buyerRepository.findAll());
    }

    @Override
    public Boolean updateBuyer(Integer buyerId, UpsertBuyerDTO buyerDTO) {
        var oldBuyer = buyerRepository.findById(buyerId).orElseThrow(()->new EntityNotFoundException("Buyer Tidak ditemukan"));
        DateTimeFormatter formatTanggal = DateTimeFormatter.ofPattern("dd MMMM yyyy", new Locale("id","ID"));
        LocalDate tanggalLahir;

        if (buyerDTO.getBirthDate()== null){
            tanggalLahir = oldBuyer.getBirthDate();
        }
        else {
            tanggalLahir = LocalDate.parse(buyerDTO.getBirthDate(),formatTanggal);
        if (tanggalLahir.isEqual(LocalDate.now()) || tanggalLahir.isAfter(LocalDate.now())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Tanggal lahir tidak boleh lebih besar dari tanggal sekarang");
            }
        }

        oldBuyer.setFirstName(buyerDTO.getFirstName() == null ? oldBuyer.getFirstName() : buyerDTO.getFirstName());
        oldBuyer.setLastName(buyerDTO.getLastName()== null ? oldBuyer.getLastName() : buyerDTO.getLastName());
        oldBuyer.setBirthDate(buyerDTO.getBirthDate() == null ? oldBuyer.getBirthDate() : tanggalLahir);
        oldBuyer.setBirthPlace(buyerDTO.getBirthPlace() == null ? oldBuyer.getBirthPlace() : buyerDTO.getBirthPlace());
        oldBuyer.setAddress(buyerDTO.getAddress() == null ? oldBuyer.getAddress() : buyerDTO.getAddress());

        buyerRepository.save(oldBuyer);

        return true;
    }

    @Override
    public Boolean deleteBuyer(Integer buyerId) {
        buyerRepository.findById(buyerId).orElseThrow(()-> new EntityNotFoundException("Buyer dengan Buyer ID "+buyerId+ " Tidak ada di database"));

        buyerRepository.deleteById(buyerId);
        return true;
    }



}
