package com.gabriel.prodmsv.model;

import lombok.Data;

@Data
public class Group {
    int id;
    String name;

    @Override
    public String toString(){
        return name;
    }
}