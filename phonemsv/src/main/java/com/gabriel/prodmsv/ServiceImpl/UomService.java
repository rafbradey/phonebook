package com.gabriel.prodmsv.ServiceImpl;

import com.gabriel.prodmsv.model.Uom;
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

public class UomService {

    Logger logger = LoggerFactory.getLogger(UomService.class);
    static UomService service=null;
    @Value("${service.api.endpoint}")
    private String endpointUrl = "http://localhost:8080/api/uom";
    RestTemplate restTemplate = null;

    public static UomService getService(){
        if(service == null){
            service=new UomService();
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

    public Uom getUom(Integer id) {
        String url = endpointUrl + "/" + Integer.toString(id);
        logger.info("getUom: " + url);

        HttpHeaders headers = new HttpHeaders();
        HttpEntity request = new HttpEntity<>(null, headers);
        final ResponseEntity<Uom> response =
                getRestTemplate().exchange(url, HttpMethod.GET, request, Uom.class);
        return response.getBody();
    }

    public Uom[] getUoms() {
        String url = endpointUrl;
        logger.info("getUoms: " + url);

        HttpHeaders headers = new HttpHeaders();
        HttpEntity request = new HttpEntity<>(null, headers);
        final ResponseEntity<Uom[]> response =
                getRestTemplate().exchange(url, HttpMethod.GET, request, Uom[].class);
        Uom[] uoms = response.getBody();
        return uoms;
    }

    //for future use - modify combobox items
    public Uom create(Uom uom) {
        String url = endpointUrl;
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<Uom> request = new HttpEntity<>(uom, headers);
        final ResponseEntity<Uom> response =
                getRestTemplate().exchange(url, HttpMethod.PUT, request, Uom.class);
        return response.getBody();
    }

    public Uom update(Uom uom) {
        logger.info("update: " + uom.toString());
        String url = endpointUrl;
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<Uom> request = new HttpEntity<>(uom, headers);
        final ResponseEntity<Uom> response =
                getRestTemplate().exchange(url, HttpMethod.POST, request, Uom.class);
        return response.getBody();
    }

    public void delete(Integer id) {
        logger.info("delete: " + Integer.toString(id));
        String url = endpointUrl + "/" + Integer.toString(id);
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<Uom> request = new HttpEntity<>(null, headers);
        final ResponseEntity<Uom> response =
                getRestTemplate().exchange(url, HttpMethod.DELETE, request, Uom.class);
    }
}