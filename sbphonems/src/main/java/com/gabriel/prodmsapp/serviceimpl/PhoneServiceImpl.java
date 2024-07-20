package com.gabriel.prodmsapp.serviceimpl;

import com.gabriel.prodmsapp.entity.PhoneData;
import com.gabriel.prodmsapp.model.Phone;
import com.gabriel.prodmsapp.repository.PhoneDataRepository;
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
    PhoneDataRepository phoneDataRepository;

    @Override
    public Phone[] getPhones() {
        List<PhoneData> phonesData = new ArrayList<>();
        List<Phone> phones = new ArrayList<>();
        phoneDataRepository.findAll().forEach(phonesData::add);
        Iterator<PhoneData> it = phonesData.iterator();

        while(it.hasNext()) {
            Phone phone = new Phone();
            PhoneData phoneData = it.next();
            phone.setId(phoneData.getId());
            phone.setName(phoneData.getName());
            phone.setPhoneNumber(phoneData.getPhoneNumber());
            phone.setGroupId(phoneData.getGroupId());
            phone.setGroupName(phoneData.getGroupName());
            phone.setEmail(phoneData.getEmail());
            phone.setMessage(phoneData.getMessage());
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
        phoneData.setPhoneNumber(phone.getPhoneNumber());
        phoneData.setGroupId(phone.getGroupId());
        phoneData.setGroupName(phone.getGroupName());
        phoneData.setEmail(phone.getEmail());
        phoneData.setMessage(phone.getMessage());
        

        phoneData = phoneDataRepository.save(phoneData);
        logger.info("add: Input"+ phoneData.toString());

        Phone newPhone = new Phone();
        newPhone.setId(phoneData.getId());
        newPhone.setName(phoneData.getName());
        newPhone.setPhoneNumber(phoneData.getPhoneNumber());
        newPhone.setGroupId(phoneData.getGroupId());
        newPhone.setGroupName(phoneData.getGroupName());
        newPhone.setEmail(phoneData.getEmail());
        newPhone.setMessage(phoneData.getMessage());
        return newPhone;
    }

    @Override
    public Phone update(Phone phone) {
        PhoneData phoneData = new PhoneData();
        phoneData.setId(phone.getId());
        phoneData.setName(phone.getName());
        phoneData.setPhoneNumber(phone.getPhoneNumber());
        phoneData.setGroupId(phone.getGroupId());
        phoneData.setGroupName(phone.getGroupName());
        phoneData.setEmail(phone.getEmail());
        phoneData.setMessage(phone.getMessage());

        phoneData = phoneDataRepository.save(phoneData);

        Phone newPhone = new Phone();
        newPhone.setId(phoneData.getId());
        newPhone.setName(phoneData.getName());
        newPhone.setPhoneNumber(phoneData.getPhoneNumber());
        newPhone.setGroupId(phoneData.getGroupId());
        newPhone.setGroupName(phoneData.getGroupName());
        newPhone.setEmail(phoneData.getEmail());
        newPhone.setMessage(phoneData.getMessage());

        return newPhone;
    }

    @Override
    public Phone getPhone(Integer id) {
        logger.info("Input id >> "+  Integer.toString(id) );
        Optional<PhoneData> optional = phoneDataRepository.findById(id);
        if(optional.isPresent()) {
            logger.info("Is present >> ");
            Phone phone = new Phone();
            PhoneData phoneDatum = optional.get();
            phone.setId(phoneDatum.getId());
            phone.setName(phoneDatum.getName());
            phone.setPhoneNumber(phoneDatum.getPhoneNumber());
            phone.setGroupId(phoneDatum.getGroupId());
            phone.setGroupName(phoneDatum.getGroupName());
            phone.setEmail(phoneDatum.getEmail());
            phone.setMessage(phoneDatum.getMessage());

            return phone;
        }
        logger.info("Failed  >> unable to locate phone" );
        return null;
    }

    @Override
    public void delete(Integer id) {
        Phone phone = null;
        logger.info("Input >> " + Integer.toString(id));
         Optional<PhoneData> optional = phoneDataRepository.findById(id);
         if( optional.isPresent()) {
             PhoneData phoneDatum = optional.get();
             phoneDataRepository.delete(optional.get());
             logger.info("Successfully deleted >> " + phoneDatum.toString());
             phone = new Phone();
             phone.setId(optional.get().getId());
             phone.setName(optional.get().getName());
             phone.setPhoneNumber(optional.get().getPhoneNumber());
             phone.setGroupId(optional.get().getGroupId());
             phone.setGroupName(optional.get().getGroupName());
             phone.setEmail(optional.get().getEmail());
             phone.setMessage(optional.get().getMessage());
         }
         else {
             logger.info("Failed  >> unable to locate phone id: " +  Integer.toString(id));
         }
    }
}
