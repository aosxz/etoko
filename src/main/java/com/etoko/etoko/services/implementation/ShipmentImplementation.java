package com.etoko.etoko.services.implementation;

import com.etoko.etoko.dao.ShipmentRepository;
import com.etoko.etoko.dtos.shipment.ShipmentGridDTO;
import com.etoko.etoko.dtos.shipment.UpsertShipmentDTO;
import com.etoko.etoko.services.abstraction.ShipmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ShipmentImplementation implements ShipmentService {

    @Autowired
    private ShipmentRepository shipmentRepository;

    @Override
    public Page<ShipmentGridDTO> findAllShipment(Integer page) {
        var pagination = PageRequest.of(page - 1, 10);
        var shipmentList = shipmentRepository.findAll();
        var shipmentStream = shipmentList.stream().skip(pagination.getOffset()).limit(pagination.getPageSize())
                .map(shipment -> {
                    return new ShipmentGridDTO(
                            shipment.getNameShipment(),
                            shipment.getDescription(),
                            shipment.getCost()
                    );
                }).collect(Collectors.toList());
        if (shipmentStream.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Data not found");
        }
        return new PageImpl<>(shipmentStream, pagination, shipmentList.size());
    }

        @Override
        public List<ShipmentGridDTO> findShipmentByName (String nameShipment){
            var shipmentList = Optional.ofNullable(shipmentRepository.findById(nameShipment).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Data tidak ditemukan")));
            var shipmentStream = shipmentList.stream()
                    .map(shipment -> new ShipmentGridDTO(
                            shipment.getNameShipment(),
                            shipment.getDescription(),
                            shipment.getCost()
                    )).collect(Collectors.toList());
            return shipmentStream;
        }

        @Override
        public List<ShipmentGridDTO> insertShipment (UpsertShipmentDTO newShipment){
            var shipment = newShipment.convert();
            shipmentRepository.save(shipment);
            return ShipmentGridDTO.toList(shipmentRepository.findAll());
        }

        @Override
        public boolean updateShipment (String shipmentId, UpsertShipmentDTO upsertShipmentDTO){
            var oldShipment = shipmentRepository.findById(shipmentId).orElseThrow(() -> new EntityNotFoundException(" Tidak dapat update karena Shipment " + shipmentId + " tidak ada"));

            oldShipment.setDescription(upsertShipmentDTO.getDescription() == null ? oldShipment.getDescription() : upsertShipmentDTO.getDescription());
            oldShipment.setCost(upsertShipmentDTO.getCost() == null ? oldShipment.getCost() : upsertShipmentDTO.getCost());

            shipmentRepository.save(oldShipment);
            return true;
        }

        @Override
        public boolean deleteShipment (String shipmentId){
            shipmentRepository.findById(shipmentId).orElseThrow(() -> new EntityNotFoundException("Shipment dengan nama " + shipmentId + " Tidak ditemukan di database"));
            shipmentRepository.deleteById(shipmentId);
            return true;
        }
    }
