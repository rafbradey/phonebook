package com.gabriel.prodmsapp.service;

import com.gabriel.prodmsapp.model.Phone;

public interface PhoneService {
    Phone[] getProducts() throws Exception;

    Phone getProduct(Integer id) throws Exception;

    Phone create(Phone phone) throws Exception;

    Phone update(Phone phone) throws Exception;

    void delete(Integer id) throws Exception;
}
