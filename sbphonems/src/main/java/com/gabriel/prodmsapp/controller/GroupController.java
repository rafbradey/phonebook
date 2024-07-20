package com.gabriel.prodmsapp.controller;

import com.gabriel.prodmsapp.model.Group;
import com.gabriel.prodmsapp.service.GroupService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class GroupController {
    Logger logger = LoggerFactory.getLogger(GroupController.class);

    @Autowired
    private GroupService groupService;

    @GetMapping("/api/group")
    public ResponseEntity<?> listGroups()
    {
        HttpHeaders headers = new HttpHeaders();
        ResponseEntity<?> response;

        try {
            Group[] groups = groupService.getGroups();
            response =  ResponseEntity.ok().headers(headers).body(groups);
        }
        catch( Exception ex)
        {
            response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
        return response;
    }

    @PutMapping("api/group")
    public ResponseEntity<?> add(@RequestBody Group group){
        logger.info("Input >> "+  group.toString() );
        HttpHeaders headers = new HttpHeaders();
        ResponseEntity<?> response;
        try {
            Group newGroup = groupService.create(group);
            logger.info("created group >> "+  newGroup.toString() );
            response = ResponseEntity.ok(newGroup);
        }
        catch( Exception ex)
        {
            logger.error("Failed to retrieve group with id : {}", ex.getMessage(), ex);
            response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
        return response;
    }

    @PostMapping("api/group")
    public ResponseEntity<?> update(@RequestBody Group group){
        logger.info("Update Input >> "+  group.toString() );
        HttpHeaders headers = new HttpHeaders();
        ResponseEntity<?> response;
        try {
            Group newGroup = groupService.update(group);
            response = ResponseEntity.ok(group);
        }
        catch( Exception ex)
        {
            logger.error("Failed to retrieve group with id : {}", ex.getMessage(), ex);
            response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
        return response;
    }

    @GetMapping("api/group/{id}")
    public ResponseEntity<?> get(@PathVariable final Integer id){
        logger.info("Input group id >> "+  Integer.toString(id));
        HttpHeaders headers = new HttpHeaders();
        ResponseEntity<?> response;
        try {
            Group group = groupService.getGroup(id);
            response = ResponseEntity.ok(group);
        }
        catch( Exception ex)
        {
            response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
        return response;
    }

    @DeleteMapping("api/group/{id}")
    public ResponseEntity<?> delete(@PathVariable final Integer id){
        logger.info("Input >> "+  Integer.toString(id));
        HttpHeaders headers = new HttpHeaders();
        ResponseEntity<?> response;
        try {
            groupService.delete(id);
            response = ResponseEntity.ok(null);
        }
        catch( Exception ex)
        {
            response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
        return response;
    }
}
