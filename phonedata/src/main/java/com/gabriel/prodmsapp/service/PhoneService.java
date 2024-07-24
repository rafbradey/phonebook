package com.gabriel.prodmsapp.service;

import com.gabriel.prodmsapp.model.Phone;
import org.springframework.web.multipart.MultipartFile;

public interface PhoneService {
    Phone[] getPhones() throws Exception;

    Phone getPhone(Integer id) throws Exception;

    Phone create(Phone phone) throws Exception;

    Phone update(Phone phone) throws Exception;

    void delete(Integer id) throws Exception;

}
