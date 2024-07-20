package com.gabriel.prodmsapp.serviceimpl;

import com.gabriel.prodmsapp.entity.SocialData;
import com.gabriel.prodmsapp.model.Social;
import com.gabriel.prodmsapp.repository.SocialDataRepository;
import com.gabriel.prodmsapp.service.SocialService;
import com.gabriel.prodmsapp.service.SocialService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Service
public class SocialServiceImpl implements SocialService {
    Logger logger = LoggerFactory.getLogger(SocialServiceImpl.class);

    @Autowired
    SocialDataRepository socialDataRepository;

    @Override
    public Social[] getSocials() {
        List<SocialData> socialsData = new ArrayList<>();
        List<Social> socials = new ArrayList<>();
        socialDataRepository.findAll().forEach(socialsData::add);
        Iterator<SocialData> it = socialsData.iterator();

        while(it.hasNext()) {
            Social social = new Social();
            SocialData socialData = it.next();
            social.setId(socialData.getId());
            social.setName(socialData.getName());
            socials.add(social);
        }

        Social[] array = new Social[socials.size()];
        for  (int i=0; i<socials.size(); i++){
            array[i] = socials.get(i);
        }
        return array;
    }

    @Override
    public Social create(Social social) {
        logger.info("add: Input"+ social.toString());
        SocialData socialData = new SocialData();
        socialData.setName(social.getName());

        socialData = socialDataRepository.save(socialData);
        logger.info("add: Input"+ socialData.toString());

        Social newSocial = new Social();
        newSocial.setId(socialData.getId());
        newSocial.setName(socialData.getName());
        return newSocial;
    }

    @Override
    public Social update(Social social) {
        SocialData socialData = new SocialData();
        socialData.setId(social.getId());
        socialData.setName(social.getName());

        socialData = socialDataRepository.save(socialData);

        Social newSocial = new Social();
        newSocial.setId(socialData.getId());
        newSocial.setName(socialData.getName());
        return newSocial;
    }

    @Override
    public Social getSocial(Integer id) {
        logger.info("Input id >> "+  Integer.toString(id) );
        Optional<SocialData> optional = socialDataRepository.findById(id);
        if(optional.isPresent()) {
            logger.info("Is present >> ");
            Social social = new Social();
            SocialData socialDatum = optional.get();
            social.setId(socialDatum.getId());
            social.setName(socialDatum.getName());
            return social;
        }
        logger.info("Failed  >> unable to locate social" );
        return null;
    }

    @Override
    public void delete(Integer id) {
        Social social = null;
        logger.info("Input >> " + Integer.toString(id));
        Optional<SocialData> optional = socialDataRepository.findById(id);
        if( optional.isPresent()) {
            SocialData socialDatum = optional.get();
            socialDataRepository.delete(optional.get());
            logger.info("Successfully deleted >> " + socialDatum.toString());
            social = new Social();
            social.setId(optional.get().getId());
            social.setName(optional.get().getName());
        }
        else {
            logger.info("Failed  >> unable to locate social id: " +  Integer.toString(id));
        }
    }
}
