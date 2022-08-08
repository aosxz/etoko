package com.etoko.etoko.dtos.buyer;

import com.etoko.etoko.entity.Buyer;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Data
public class UpsertBuyerDTO {

    @NotBlank(message = "Firstname is required")
    private String firstName;
    private String lastName;
    private String birthDate;
    private String birthPlace;
    private String address;


    public Buyer convert(){
        DateTimeFormatter formatTanggal = DateTimeFormatter.ofPattern("dd MMMM yyyy",new Locale("id","ID"));
        return new Buyer(
                firstName,
                lastName,
                LocalDate.parse(birthDate,formatTanggal),
                birthPlace,
                address
        );

    }
}
