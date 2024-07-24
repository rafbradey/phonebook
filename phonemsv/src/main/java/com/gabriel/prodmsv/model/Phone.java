package com.gabriel.prodmsv.model;

import lombok.Data;

import java.util.Date;

@Data
public class Phone {
    int id;
    String name;
    String phoneNumber;
    String email;
    String message;
    String company;
    String account;
    Date birthday;


    //support table
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