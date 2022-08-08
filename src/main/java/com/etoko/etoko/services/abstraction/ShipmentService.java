package com.etoko.etoko.services.abstraction;

import com.etoko.etoko.dtos.shipment.ShipmentGridDTO;
import com.etoko.etoko.dtos.shipment.UpsertShipmentDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ShipmentService {

    public Page<ShipmentGridDTO> findAllShipment(Integer page);

    public List<ShipmentGridDTO>findShipmentByName(String nameShipment);

    public List<ShipmentGridDTO>insertShipment(UpsertShipmentDTO newShipment);

    public boolean updateShipment(String shipmentId, UpsertShipmentDTO upsertShipmentDTO);

    public boolean deleteShipment(String shipmentId);
}
