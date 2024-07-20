package com.gabriel.prodmsv.model;

import lombok.Data;

@Data
public class Product {
    int id;
    String name;
    String description;
    int uomId;
    String uomName;

    @Override
    public String toString(){
        return name;
    }
}