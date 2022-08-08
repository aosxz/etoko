package com.etoko.etoko.dtos.buyer;


import com.etoko.etoko.entity.Buyer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BuyerGridDTO {

    private Integer buyerId;
    private String firstName;
    private String lastName;
    private String birthDate;
    private String birthPlace;
    private String address;




    public static BuyerGridDTO set(Buyer buyer){
        DateTimeFormatter formatTanggal = DateTimeFormatter.ofPattern("dd MMMM yyyy",new Locale("id","ID"));
        return  new BuyerGridDTO(
                buyer.getBuyerId(),
                buyer.getFirstName(),
                buyer.getLastName(),
                formatTanggal.format(buyer.getBirthDate()),
                buyer.getBirthPlace(),
                buyer.getAddress()
        );
    }

    public static List<BuyerGridDTO>toList(List<Buyer>buyers){
        List<BuyerGridDTO>result = new ArrayList<>();
        for (Buyer buyer : buyers){
            result.add(set(buyer));
        }
        return result;
    }
}
