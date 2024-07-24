package com.gabriel.prodmsv.model;

import lombok.Data;

@Data
public class Image {
    int id;
    String imageURL;

    @Override
    public String toString(){
        return imageURL;
    }
}