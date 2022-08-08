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
public class ShipmentGridDTO {

    private  String nameShipment;
    private  String description;
    private  BigDecimal cost;

    public static ShipmentGridDTO set(Shipment shipment) {
        return new ShipmentGridDTO(
                shipment.getNameShipment(),
                shipment.getDescription(),
                shipment.getCost());
    }

    public static List<ShipmentGridDTO> toList(List<Shipment> shipments) {
        List<ShipmentGridDTO> result = new ArrayList<>();
        for (Shipment shipment : shipments) {
            result.add(set(shipment));
        }
        return result;
    }
}
