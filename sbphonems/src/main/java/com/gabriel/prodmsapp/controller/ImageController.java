package com.gabriel.prodmsapp.controller;

import com.gabriel.prodmsapp.model.Image;
import com.gabriel.prodmsapp.service.ImageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ImageController {
    Logger logger = LoggerFactory.getLogger(ImageController.class);

    @Autowired
    private ImageService imageService;

    @GetMapping("/api/image")
    public ResponseEntity<?> listImages()
    {
        HttpHeaders headers = new HttpHeaders();
        ResponseEntity<?> response;

        try {
            Image[] images = imageService.getImages();
            response =  ResponseEntity.ok().headers(headers).body(images);
        }
        catch( Exception ex)
        {
            response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
        return response;
    }

    @PutMapping("api/image")
    public ResponseEntity<?> add(@RequestBody Image image){
        logger.info("Input >> "+  image.toString() );
        HttpHeaders headers = new HttpHeaders();
        ResponseEntity<?> response;
        try {
            Image newImage = imageService.create(image);
            logger.info("created image >> "+  newImage.toString() );
            response = ResponseEntity.ok(newImage);
        }
        catch( Exception ex)
        {
            logger.error("Failed to retrieve image with id : {}", ex.getMessage(), ex);
            response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
        return response;
    }

    @PostMapping("api/image")
    public ResponseEntity<?> update(@RequestBody Image image){
        logger.info("Update Input >> "+  image.toString() );
        HttpHeaders headers = new HttpHeaders();
        ResponseEntity<?> response;
        try {
            Image newImage = imageService.update(image);
            response = ResponseEntity.ok(image);
        }
        catch( Exception ex)
        {
            logger.error("Failed to retrieve image with id : {}", ex.getMessage(), ex);
            response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
        return response;
    }

    @GetMapping("api/image/{id}")
    public ResponseEntity<?> get(@PathVariable final Integer id){
        logger.info("Input image id >> "+  Integer.toString(id));
        HttpHeaders headers = new HttpHeaders();
        ResponseEntity<?> response;
        try {
            Image image = imageService.getImage(id);
            response = ResponseEntity.ok(image);
        }
        catch( Exception ex)
        {
            response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
        return response;
    }

    @DeleteMapping("api/image/{id}")
    public ResponseEntity<?> delete(@PathVariable final Integer id){
        logger.info("Input >> "+  Integer.toString(id));
        HttpHeaders headers = new HttpHeaders();
        ResponseEntity<?> response;
        try {
            imageService.delete(id);
            response = ResponseEntity.ok(null);
        }
        catch( Exception ex)
        {
            response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
        return response;
    }
}
