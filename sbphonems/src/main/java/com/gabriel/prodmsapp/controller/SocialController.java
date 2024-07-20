package com.gabriel.prodmsapp.controller;

import com.gabriel.prodmsapp.model.Social;
import com.gabriel.prodmsapp.service.SocialService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class SocialController {
    Logger logger = LoggerFactory.getLogger(SocialController.class);

    @Autowired
    private SocialService socialService;

    @GetMapping("/api/social")
    public ResponseEntity<?> listSocials()
    {
        HttpHeaders headers = new HttpHeaders();
        ResponseEntity<?> response;

        try {
            Social[] socials = socialService.getSocials();
            response =  ResponseEntity.ok().headers(headers).body(socials);
        }
        catch( Exception ex)
        {
            response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
        return response;
    }

    @PutMapping("api/social")
    public ResponseEntity<?> add(@RequestBody Social social){
        logger.info("Input >> "+  social.toString() );
        HttpHeaders headers = new HttpHeaders();
        ResponseEntity<?> response;
        try {
            Social newSocial = socialService.create(social);
            logger.info("created social >> "+  newSocial.toString() );
            response = ResponseEntity.ok(newSocial);
        }
        catch( Exception ex)
        {
            logger.error("Failed to retrieve social with id : {}", ex.getMessage(), ex);
            response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
        return response;
    }

    @PostMapping("api/social")
    public ResponseEntity<?> update(@RequestBody Social social){
        logger.info("Update Input >> "+  social.toString() );
        HttpHeaders headers = new HttpHeaders();
        ResponseEntity<?> response;
        try {
            Social newSocial = socialService.update(social);
            response = ResponseEntity.ok(social);
        }
        catch( Exception ex)
        {
            logger.error("Failed to retrieve social with id : {}", ex.getMessage(), ex);
            response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
        return response;
    }

    @GetMapping("api/social/{id}")
    public ResponseEntity<?> get(@PathVariable final Integer id){
        logger.info("Input social id >> "+  Integer.toString(id));
        HttpHeaders headers = new HttpHeaders();
        ResponseEntity<?> response;
        try {
            Social social = socialService.getSocial(id);
            response = ResponseEntity.ok(social);
        }
        catch( Exception ex)
        {
            response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
        return response;
    }

    @DeleteMapping("api/social/{id}")
    public ResponseEntity<?> delete(@PathVariable final Integer id){
        logger.info("Input >> "+  Integer.toString(id));
        HttpHeaders headers = new HttpHeaders();
        ResponseEntity<?> response;
        try {
            socialService.delete(id);
            response = ResponseEntity.ok(null);
        }
        catch( Exception ex)
        {
            response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
        return response;
    }
}
