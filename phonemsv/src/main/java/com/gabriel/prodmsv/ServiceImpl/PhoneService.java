package com.gabriel.prodmsv.ServiceImpl;

import com.gabriel.prodmsv.model.Phone;
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

public class PhoneService {
    Logger logger = LoggerFactory.getLogger(PhoneService.class);
    static PhoneService service=null;
    @Value("${service.api.endpoint}")
    private String endpointUrl = "http://localhost:8080/api/phone";
    RestTemplate restTemplate = null;

    public static PhoneService getService(){
        if(service == null){
            service=new PhoneService();
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

    public Phone getProduct(Integer id) {
        String url = endpointUrl + "/" + Integer.toString(id);
        logger.info("getProduct: " + url);

        HttpHeaders headers = new HttpHeaders();
        HttpEntity request = new HttpEntity<>(null, headers);
        final ResponseEntity<Phone> response =
                getRestTemplate().exchange(url, HttpMethod.GET, request, Phone.class);
        return response.getBody();
    }

    public Phone[] getProducts() {
        String url = endpointUrl;
        logger.info("getProducts: " + url);

        HttpHeaders headers = new HttpHeaders();
        HttpEntity request = new HttpEntity<>(null, headers);
        final ResponseEntity<Phone[]> response =
                getRestTemplate().exchange(url, HttpMethod.GET, request, Phone[].class);
        Phone[] phones = response.getBody();
        return phones;
    }

    public Phone create(Phone phone) {
        String url = endpointUrl;
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<Phone> request = new HttpEntity<>(phone, headers);
        final ResponseEntity<Phone> response =
                getRestTemplate().exchange(url, HttpMethod.PUT, request, Phone.class);
        return response.getBody();
    }

    public Phone update(Phone phone) {
        logger.info("update: " + phone.toString());
        String url = endpointUrl;
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<Phone> request = new HttpEntity<>(phone, headers);
        final ResponseEntity<Phone> response =
                getRestTemplate().exchange(url, HttpMethod.POST, request, Phone.class);
        return response.getBody();
    }

    public void delete(Integer id) {
        logger.info("delete: " + Integer.toString(id));
        String url = endpointUrl + "/" + Integer.toString(id);
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<Phone> request = new HttpEntity<>(null, headers);
        final ResponseEntity<Phone> response =
                getRestTemplate().exchange(url, HttpMethod.DELETE, request, Phone.class);
    }
}