package com.gabriel.prodmsapp.model;

import lombok.Data;

@Data
public class Phone {
    int id;
    String name;
    String phoneNumber;
    String email;
    String message;

    //support tables
    int groupId;
    String groupName;

    int socialId;
    String socialName;

    int imageId;
    String imageURL;


    @Override
    public String toString(){
        return name;
    }
}