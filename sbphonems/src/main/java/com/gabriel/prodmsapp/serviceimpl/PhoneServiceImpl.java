package com.gabriel.prodmsapp.serviceimpl;

import com.gabriel.prodmsapp.entity.PhoneData;
import com.gabriel.prodmsapp.model.Phone;
import com.gabriel.prodmsapp.repository.ProductDataRepository;
import com.gabriel.prodmsapp.service.PhoneService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PhoneServiceImpl implements PhoneService {
    Logger logger = LoggerFactory.getLogger(PhoneServiceImpl.class);

    @Autowired
    ProductDataRepository productDataRepository;

    @Override
    public Phone[] getProducts() {
        List<PhoneData> productsData = new ArrayList<>();
        List<Phone> phones = new ArrayList<>();
        productDataRepository.findAll().forEach(productsData::add);
        Iterator<PhoneData> it = productsData.iterator();

        while(it.hasNext()) {
            Phone phone = new Phone();
            PhoneData phoneData = it.next();
            phone.setId(phoneData.getId());
            phone.setName(phoneData.getName());
            phone.setDescription(phoneData.getDescription());
            phone.setGroupId(phoneData.getGroupId());
            phone.setGroupName(phoneData.getGroupName());
            phones.add(phone);
        }

        Phone[] array = new Phone[phones.size()];
        for  (int i = 0; i< phones.size(); i++){
            array[i] = phones.get(i);
        }
//        Phone[] array = (Phone[])phones.toArray();
        return array;
    }

    @Override
    public Phone create(Phone phone) {
        logger.info("add: Input"+ phone.toString());
        PhoneData phoneData = new PhoneData();
        phoneData.setName(phone.getName());
        phoneData.setDescription(phone.getDescription());
        phoneData.setGroupId(phone.getGroupId());
        phoneData.setGroupName(phone.getGroupName());

        phoneData = productDataRepository.save(phoneData);
        logger.info("add: Input"+ phoneData.toString());

        Phone newPhone = new Phone();
        newPhone.setId(phoneData.getId());
        newPhone.setName(phoneData.getName());
        newPhone.setDescription(phoneData.getDescription());
        newPhone.setGroupId(phoneData.getGroupId());
        newPhone.setGroupName(phoneData.getGroupName());
        return newPhone;
    }

    @Override
    public Phone update(Phone phone) {
        PhoneData phoneData = new PhoneData();
        phoneData.setId(phone.getId());
        phoneData.setName(phone.getName());
        phoneData.setDescription(phone.getDescription());
        phoneData.setGroupId(phone.getGroupId());
        phoneData.setGroupName(phone.getGroupName());

        phoneData = productDataRepository.save(phoneData);

        Phone newPhone = new Phone();
        newPhone.setId(phoneData.getId());
        newPhone.setName(phoneData.getName());
        newPhone.setDescription(phoneData.getDescription());
        newPhone.setGroupId(phoneData.getGroupId());
        newPhone.setGroupName(phoneData.getGroupName());
        return newPhone;
    }

    @Override
    public Phone getProduct(Integer id) {
        logger.info("Input id >> "+  Integer.toString(id) );
        Optional<PhoneData> optional = productDataRepository.findById(id);
        if(optional.isPresent()) {
            logger.info("Is present >> ");
            Phone phone = new Phone();
            PhoneData productDatum = optional.get();
            phone.setId(productDatum.getId());
            phone.setName(productDatum.getName());
            phone.setDescription(productDatum.getDescription());
            phone.setGroupId(productDatum.getGroupId());
            phone.setGroupName(productDatum.getGroupName());
            return phone;
        }
        logger.info("Failed  >> unable to locate product" );
        return null;
    }

    @Override
    public void delete(Integer id) {
        Phone phone = null;
        logger.info("Input >> " + Integer.toString(id));
         Optional<PhoneData> optional = productDataRepository.findById(id);
         if( optional.isPresent()) {
             PhoneData productDatum = optional.get();
             productDataRepository.delete(optional.get());
             logger.info("Successfully deleted >> " + productDatum.toString());
             phone = new Phone();
             phone.setId(optional.get().getId());
             phone.setName(optional.get().getName());
             phone.setDescription(optional.get().getDescription());
             phone.setGroupId(optional.get().getGroupId());
             phone.setGroupName(optional.get().getGroupName());
         }
         else {
             logger.info("Failed  >> unable to locate phone id: " +  Integer.toString(id));
         }
    }
}
