package com.gabriel.prodmsapp.serviceimpl;

import com.gabriel.prodmsapp.entity.UomData;
import com.gabriel.prodmsapp.model.Uom;
import com.gabriel.prodmsapp.repository.UomDataRepository;
import com.gabriel.prodmsapp.service.UomService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Service
public class UomServiceImpl implements UomService {
    Logger logger = LoggerFactory.getLogger(UomServiceImpl.class);

    @Autowired
    UomDataRepository uomDataRepository;

    @Override
    public Uom[] getUoms() {
        List<UomData> uomsData = new ArrayList<>();
        List<Uom> uoms = new ArrayList<>();
        uomDataRepository.findAll().forEach(uomsData::add);
        Iterator<UomData> it = uomsData.iterator();

        while(it.hasNext()) {
            Uom uom = new Uom();
            UomData uomData = it.next();
            uom.setId(uomData.getId());
            uom.setName(uomData.getName());
            uoms.add(uom);
        }

        Uom[] array = new Uom[uoms.size()];
        for  (int i=0; i<uoms.size(); i++){
            array[i] = uoms.get(i);
        }
        return array;
    }

    @Override
    public Uom create(Uom uom) {
        logger.info("add: Input"+ uom.toString());
        UomData uomData = new UomData();
        uomData.setName(uom.getName());

        uomData = uomDataRepository.save(uomData);
        logger.info("add: Input"+ uomData.toString());

        Uom newUom = new Uom();
        newUom.setId(uomData.getId());
        newUom.setName(uomData.getName());
        return newUom;
    }

    @Override
    public Uom update(Uom uom) {
        UomData uomData = new UomData();
        uomData.setId(uom.getId());
        uomData.setName(uom.getName());

        uomData = uomDataRepository.save(uomData);

        Uom newUom = new Uom();
        newUom.setId(uomData.getId());
        newUom.setName(uomData.getName());
        return newUom;
    }

    @Override
    public Uom getUom(Integer id) {
        logger.info("Input id >> "+  Integer.toString(id) );
        Optional<UomData> optional = uomDataRepository.findById(id);
        if(optional.isPresent()) {
            logger.info("Is present >> ");
            Uom uom = new Uom();
            UomData uomDatum = optional.get();
            uom.setId(uomDatum.getId());
            uom.setName(uomDatum.getName());
             return uom;
        }
        logger.info("Failed  >> unable to locate uom" );
        return null;
    }

    @Override
    public void delete(Integer id) {
        Uom uom = null;
        logger.info("Input >> " + Integer.toString(id));
         Optional<UomData> optional = uomDataRepository.findById(id);
         if( optional.isPresent()) {
             UomData uomDatum = optional.get();
             uomDataRepository.delete(optional.get());
             logger.info("Successfully deleted >> " + uomDatum.toString());
             uom = new Uom();
             uom.setId(optional.get().getId());
             uom.setName(optional.get().getName());
         }
         else {
             logger.info("Failed  >> unable to locate uom id: " +  Integer.toString(id));
         }
    }
}
