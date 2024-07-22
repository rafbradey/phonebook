package com.gabriel.prodmsv.ServiceImpl;

import com.gabriel.prodmsv.model.Social;
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

public class SocialService {

    Logger logger = LoggerFactory.getLogger(SocialService.class);
    static SocialService service=null;
    @Value("${service.api.endpoint}")
    private String endpointUrl = "http://localhost:8080/api/social";
    RestTemplate restTemplate = null;

    public static SocialService getService(){
        if(service == null){
            service=new SocialService();
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

    public Social getSocial(Integer id) {
        String url = endpointUrl + "/" + Integer.toString(id);
        logger.info("getSocial: " + url);

        HttpHeaders headers = new HttpHeaders();
        HttpEntity request = new HttpEntity<>(null, headers);
        final ResponseEntity<Social> response =
                getRestTemplate().exchange(url, HttpMethod.GET, request, Social.class);
        return response.getBody();
    }

    public Social[] getSocials() {
        String url = endpointUrl;
        logger.info("getSocials: " + url);

        HttpHeaders headers = new HttpHeaders();
        HttpEntity request = new HttpEntity<>(null, headers);
        final ResponseEntity<Social[]> response =
                getRestTemplate().exchange(url, HttpMethod.GET, request, Social[].class);
        Social[] socials = response.getBody();
        return socials;
    }

    //for future use - modify combobox items
    public Social create(Social social) {
        String url = endpointUrl;
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<Social> request = new HttpEntity<>(social, headers);
        final ResponseEntity<Social> response =
                getRestTemplate().exchange(url, HttpMethod.PUT, request, Social.class);
        return response.getBody();
    }

    public Social update(Social social) {
        logger.info("update: " + social.toString());
        String url = endpointUrl;
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<Social> request = new HttpEntity<>(social, headers);
        final ResponseEntity<Social> response =
                getRestTemplate().exchange(url, HttpMethod.POST, request, Social.class);
        return response.getBody();
    }

    public void delete(Integer id) {
        logger.info("delete: " + Integer.toString(id));
        String url = endpointUrl + "/" + Integer.toString(id);
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<Social> request = new HttpEntity<>(null, headers);
        final ResponseEntity<Social> response =
                getRestTemplate().exchange(url, HttpMethod.DELETE, request, Social.class);
    }
}