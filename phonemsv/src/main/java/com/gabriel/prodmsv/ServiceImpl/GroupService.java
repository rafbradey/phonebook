package com.gabriel.prodmsv.ServiceImpl;

import com.gabriel.prodmsv.model.Group;
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

public class GroupService {

    Logger logger = LoggerFactory.getLogger(GroupService.class);
    static GroupService service=null;
    @Value("${service.api.endpoint}")
    private String endpointUrl = "http://localhost:8080/api/group";
    RestTemplate restTemplate = null;

    public static GroupService getService(){
        if(service == null){
            service=new GroupService();
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

    public Group getGroup(Integer id) {
        String url = endpointUrl + "/" + Integer.toString(id);
        logger.info("getGroup: " + url);

        HttpHeaders headers = new HttpHeaders();
        HttpEntity request = new HttpEntity<>(null, headers);
        final ResponseEntity<Group> response =
                getRestTemplate().exchange(url, HttpMethod.GET, request, Group.class);
        return response.getBody();
    }

    public Group[] getGroups() {
        String url = endpointUrl;
        logger.info("getGroups: " + url);

        HttpHeaders headers = new HttpHeaders();
        HttpEntity request = new HttpEntity<>(null, headers);
        final ResponseEntity<Group[]> response =
                getRestTemplate().exchange(url, HttpMethod.GET, request, Group[].class);
        Group[] groups = response.getBody();
        return groups;
    }

    //for future use - modify combobox items
    public Group create(Group group) {
        String url = endpointUrl;
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<Group> request = new HttpEntity<>(group, headers);
        final ResponseEntity<Group> response =
                getRestTemplate().exchange(url, HttpMethod.PUT, request, Group.class);
        return response.getBody();
    }

    public Group update(Group group) {
        logger.info("update: " + group.toString());
        String url = endpointUrl;
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<Group> request = new HttpEntity<>(group, headers);
        final ResponseEntity<Group> response =
                getRestTemplate().exchange(url, HttpMethod.POST, request, Group.class);
        return response.getBody();
    }

    public void delete(Integer id) {
        logger.info("delete: " + Integer.toString(id));
        String url = endpointUrl + "/" + Integer.toString(id);
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<Group> request = new HttpEntity<>(null, headers);
        final ResponseEntity<Group> response =
                getRestTemplate().exchange(url, HttpMethod.DELETE, request, Group.class);
    }
}