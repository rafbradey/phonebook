package com.gabriel.prodmsapp.service;

import com.gabriel.prodmsapp.model.Image;

public interface ImageService {
    Image[] getImages() throws Exception;

    Image getImage(Integer id) throws Exception;

    Image create(Image social) throws Exception;

    Image update(Image social) throws Exception;

    void delete(Integer id) throws Exception;
}
