package com.gabriel.prodmsv.model;

import lombok.Data;

@Data
public class Phone {
    int id;
    String name;
    String phoneNumber;
    String email;
    String message;

    //support table
    int groupId;
    String groupName;

    int socialId;
    String socialName;

    @Override
    public String toString(){
        return name;
    }
}