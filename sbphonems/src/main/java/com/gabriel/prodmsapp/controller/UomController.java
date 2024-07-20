package com.gabriel.prodmsapp.controller;

import com.gabriel.prodmsapp.model.Uom;
import com.gabriel.prodmsapp.service.UomService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UomController {
    Logger logger = LoggerFactory.getLogger(UomController.class);

    @Autowired
    private UomService uomService;

    @GetMapping("/api/uom")
    public ResponseEntity<?> listUoms()
    {
        HttpHeaders headers = new HttpHeaders();
        ResponseEntity<?> response;

        try {
            Uom[] uoms = uomService.getUoms();
            response =  ResponseEntity.ok().headers(headers).body(uoms);
        }
        catch( Exception ex)
        {
            response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
        return response;
    }

    @PutMapping("api/uom")
    public ResponseEntity<?> add(@RequestBody Uom uom){
        logger.info("Input >> "+  uom.toString() );
        HttpHeaders headers = new HttpHeaders();
        ResponseEntity<?> response;
        try {
            Uom newUom = uomService.create(uom);
            logger.info("created uom >> "+  newUom.toString() );
            response = ResponseEntity.ok(newUom);
        }
        catch( Exception ex)
        {
            logger.error("Failed to retrieve uom with id : {}", ex.getMessage(), ex);
            response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
        return response;
    }

    @PostMapping("api/uom")
    public ResponseEntity<?> update(@RequestBody Uom uom){
        logger.info("Update Input >> "+  uom.toString() );
        HttpHeaders headers = new HttpHeaders();
        ResponseEntity<?> response;
        try {
            Uom newUom = uomService.update(uom);
            response = ResponseEntity.ok(uom);
        }
        catch( Exception ex)
        {
            logger.error("Failed to retrieve uom with id : {}", ex.getMessage(), ex);
            response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
        return response;
    }

    @GetMapping("api/uom/{id}")
    public ResponseEntity<?> get(@PathVariable final Integer id){
        logger.info("Input uom id >> "+  Integer.toString(id));
        HttpHeaders headers = new HttpHeaders();
        ResponseEntity<?> response;
        try {
            Uom uom = uomService.getUom(id);
            response = ResponseEntity.ok(uom);
        }
        catch( Exception ex)
        {
            response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
        return response;
    }

    @DeleteMapping("api/uom/{id}")
    public ResponseEntity<?> delete(@PathVariable final Integer id){
        logger.info("Input >> "+  Integer.toString(id));
        HttpHeaders headers = new HttpHeaders();
        ResponseEntity<?> response;
        try {
            uomService.delete(id);
            response = ResponseEntity.ok(null);
        }
        catch( Exception ex)
        {
            response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
        return response;
    }
}
