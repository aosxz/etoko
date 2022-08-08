package com.etoko.etoko.controllers;

import com.etoko.etoko.dtos.RestResponse;
import com.etoko.etoko.dtos.shipment.ShipmentGridDTO;
import com.etoko.etoko.dtos.shipment.UpsertShipmentDTO;
import com.etoko.etoko.services.abstraction.ShipmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/shipment")
public class ShipmentController {

    @Autowired
    private ShipmentService shipmentService;

    @GetMapping("get")
    public ResponseEntity<RestResponse<Page<ShipmentGridDTO>>> findAll(Integer page) {
        return new ResponseEntity<>(
                new RestResponse<>(shipmentService.findAllShipment(page),
                        "Shipment Berhasil ditemukan",
                        200),
                HttpStatus.OK);
    }

    @GetMapping("find-by-id/{nameShipment}")
    public ResponseEntity<RestResponse<List<ShipmentGridDTO>>> findShipmentById(String nameShipment) {
        return new ResponseEntity<>(
                new RestResponse<>(shipmentService.findShipmentByName(nameShipment),
                        "Shipment Name : " + nameShipment + " berhasil ditemukan",
                        200),
                HttpStatus.OK
        );

    }

    @PostMapping("insert")
    public ResponseEntity<RestResponse<ShipmentGridDTO>> insertShipment(@RequestBody UpsertShipmentDTO newShipment) {
        return new ResponseEntity(
                new RestResponse<>(shipmentService.insertShipment(newShipment),
                        "Shipment berhasil ditambahkan",
                        201),
                HttpStatus.CREATED);

    }

    @PutMapping("update/{nameShipment}")
    public ResponseEntity<RestResponse> updateShipment(@PathVariable String nameShipment,@RequestBody UpsertShipmentDTO shipmentUpdateDTO) {
        return new ResponseEntity(
                new RestResponse(shipmentService.updateShipment(nameShipment, shipmentUpdateDTO),
                        "Shipment Name: " + nameShipment + " berhasil di update ",
                        201),
                HttpStatus.CREATED);

    }

    @DeleteMapping("delete/{nameShipment}")
    public ResponseEntity<RestResponse> deleteShipment(@PathVariable String nameShipment) {
        return new ResponseEntity(
                new RestResponse(shipmentService.deleteShipment(nameShipment),
                        "Shipment Name: " + nameShipment + " berhasil dihapus ",
                        201),
                HttpStatus.CREATED);

    }

}
