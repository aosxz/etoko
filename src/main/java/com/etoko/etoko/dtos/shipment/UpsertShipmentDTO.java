package com.etoko.etoko.dtos.shipment;

import com.etoko.etoko.entity.Shipment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpsertShipmentDTO {

    private  String nameShipment;
    private  String description;
    private  BigDecimal cost;

    public Shipment convert() {
        return new Shipment(
                nameShipment,
                description,
                cost);
    }
}
