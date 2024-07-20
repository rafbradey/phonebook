package com.gabriel.prodmsapp.model;

import lombok.Data;

@Data
public class Phone {
    int id;
    String name;
    String description;

    int groupId;
    String groupName;

    int socialId;
    String socialName;


    @Override
    public String toString(){
        return name;
    }
}