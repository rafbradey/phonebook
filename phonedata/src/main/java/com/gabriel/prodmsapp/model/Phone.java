package com.gabriel.prodmsapp.model;

import lombok.Data;

@Data
public class Phone {
    int id;
    String name;
    String phoneNumber;
    String email;
    String message;

    int groupId;
    String groupName;

    int socialId;
    String socialName;


    @Override
    public String toString(){
        return name;
    }
}