package com.gabriel.prodmsapp.model;

import lombok.Data;

@Data
public class Uom {
    int id;
    String name;

    @Override
    public String toString(){
        return name;
    }
}