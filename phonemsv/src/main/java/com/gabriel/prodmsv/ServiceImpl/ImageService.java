package com.gabriel.prodmsv.ServiceImpl;

import com.gabriel.prodmsv.model.Image;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ImageService {

    Logger logger = LoggerFactory.getLogger(ImageService.class);
    static ImageService service=null;
    @Value("${service.api.endpoint}")
    private String endpointUrl = "http://localhost:8080/api/image";
    RestTemplate restTemplate = null;

    public static ImageService getService(){
        if(service == null){
            service=new ImageService();
        }
        return service;
    }
    public RestTemplate getRestTemplate() {
        if (restTemplate == null) {
            restTemplate = new RestTemplate();
            List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
            MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
            converter.setSupportedMediaTypes(Collections.singletonList(MediaType.APPLICATION_JSON));
            messageConverters.add(converter);
            restTemplate.setMessageConverters(messageConverters);
        }
        return restTemplate;
    }

    public Image getImage(Integer id) {
        String url = endpointUrl + "/" + Integer.toString(id);
        logger.info("getImage: " + url);

        HttpHeaders headers = new HttpHeaders();
        HttpEntity request = new HttpEntity<>(null, headers);
        final ResponseEntity<Image> response =
                getRestTemplate().exchange(url, HttpMethod.GET, request, Image.class);
        return response.getBody();
    }

    public Image[] getImages() {
        String url = endpointUrl;
        logger.info("getImages: " + url);

        HttpHeaders headers = new HttpHeaders();
        HttpEntity request = new HttpEntity<>(null, headers);
        final ResponseEntity<Image[]> response =
                getRestTemplate().exchange(url, HttpMethod.GET, request, Image[].class);
        Image[] images = response.getBody();
        return images;
    }

    //for future use - modify combobox items
    public Image create(Image image) {
        String url = endpointUrl;
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<Image> request = new HttpEntity<>(image, headers);
        final ResponseEntity<Image> response =
                getRestTemplate().exchange(url, HttpMethod.PUT, request, Image.class);
        return response.getBody();
    }

    public Image update(Image image) {
        logger.info("update: " + image.toString());
        String url = endpointUrl;
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<Image> request = new HttpEntity<>(image, headers);
        final ResponseEntity<Image> response =
                getRestTemplate().exchange(url, HttpMethod.POST, request, Image.class);
        return response.getBody();
    }

    public void delete(Integer id) {
        logger.info("delete: " + Integer.toString(id));
        String url = endpointUrl + "/" + Integer.toString(id);
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<Image> request = new HttpEntity<>(null, headers);
        final ResponseEntity<Image> response =
                getRestTemplate().exchange(url, HttpMethod.DELETE, request, Image.class);
    }
}