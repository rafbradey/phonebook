package com.gabriel.prodmsapp.controller;

import com.gabriel.prodmsapp.model.Phone;
import com.gabriel.prodmsapp.service.PhoneService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class PhoneController {
    Logger logger = LoggerFactory.getLogger(PhoneController.class);

    @Autowired
    private PhoneService phoneService;

    @GetMapping("/api/phone")
    public ResponseEntity<?> listPhones()
    {
        HttpHeaders headers = new HttpHeaders();
        ResponseEntity<?> response;

        try {
            Phone[] phones = phoneService.getPhones();
            response =  ResponseEntity.ok().headers(headers).body(phones);
        }
        catch( Exception ex)
        {
            response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
        return response;
    }

    @PutMapping("api/phone")
    public ResponseEntity<?> add(@RequestBody Phone phone){
        logger.info("Input >> "+  phone.toString() );
        HttpHeaders headers = new HttpHeaders();
        ResponseEntity<?> response;
        try {
            Phone newPhone = phoneService.create(phone);
            logger.info("created phone >> "+  newPhone.toString() );
            response = ResponseEntity.ok(newPhone);
        }
        catch( Exception ex)
        {
            logger.error("Failed to retrieve phone with id : {}", ex.getMessage(), ex);
            response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
        return response;
    }

    @PostMapping("api/phone")
    public ResponseEntity<?> update(@RequestBody Phone phone){
        logger.info("Update Input >> "+  phone.toString() );
        HttpHeaders headers = new HttpHeaders();
        ResponseEntity<?> response;
        try {
            Phone newPhone = phoneService.update(phone);
            response = ResponseEntity.ok(phone);
        }
        catch( Exception ex)
        {
            logger.error("Failed to retrieve phone with id : {}", ex.getMessage(), ex);
            response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
        return response;
    }

    @GetMapping("api/phone/{id}")
    public ResponseEntity<?> get(@PathVariable final Integer id){
        logger.info("Input phone id >> "+  Integer.toString(id));
        HttpHeaders headers = new HttpHeaders();
        ResponseEntity<?> response;
        try {
            Phone phone = phoneService.getPhone(id);
            response = ResponseEntity.ok(phone);
        }
        catch( Exception ex)
        {
            response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
        return response;
    }

    @DeleteMapping("api/phone/{id}")
    public ResponseEntity<?> delete(@PathVariable final Integer id){
        logger.info("Input >> "+  Integer.toString(id));
        HttpHeaders headers = new HttpHeaders();
        ResponseEntity<?> response;
        try {
            phoneService.delete(id);
            response = ResponseEntity.ok(null);
        }
        catch( Exception ex)
        {
            response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
        return response;
    }
}
