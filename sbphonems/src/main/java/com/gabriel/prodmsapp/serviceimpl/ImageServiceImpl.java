package com.gabriel.prodmsapp.serviceimpl;

import com.gabriel.prodmsapp.entity.ImageData;
import com.gabriel.prodmsapp.model.Image;
import com.gabriel.prodmsapp.repository.ImageDataRepository;
import com.gabriel.prodmsapp.service.ImageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Service
public class ImageServiceImpl implements ImageService {
    Logger logger = LoggerFactory.getLogger(ImageServiceImpl.class);

    @Autowired
    ImageDataRepository imageDataRepository;

    @Override
    public Image[] getImages() {
        List<ImageData> imagesData = new ArrayList<>();
        List<Image> images = new ArrayList<>();
        imageDataRepository.findAll().forEach(imagesData::add);
        Iterator<ImageData> it = imagesData.iterator();

        while(it.hasNext()) {
            Image image = new Image();
            ImageData imageData = it.next();
            image.setId(imageData.getId());
            image.setImageURL(imageData.getImageURL());
            images.add(image);
        }

        Image[] array = new Image[images.size()];
        for  (int i = 0; i< images.size(); i++){
            array[i] = images.get(i);
        }
        return array;
    }

    @Override
    public Image create(Image image) {
        logger.info("add: Input"+ image.toString());
        ImageData imageData = new ImageData();
        imageData.setImageURL(image.getImageURL());

        imageData = imageDataRepository.save(imageData);
        logger.info("add: Input"+ imageData.toString());

        Image newImage = new Image();
        newImage.setId(imageData.getId());
        newImage.setImageURL(imageData.getImageURL());
        return newImage;
    }

    @Override
    public Image update(Image image) {
        ImageData imageData = new ImageData();
        imageData.setId(image.getId());
        imageData.setImageURL(image.getImageURL());

        imageData = imageDataRepository.save(imageData);

        Image newImage = new Image();
        newImage.setId(imageData.getId());
        newImage.setImageURL(imageData.getImageURL());
        return newImage;
    }

    @Override
    public Image getImage(Integer id) {
        logger.info("Input id >> "+  Integer.toString(id) );
        Optional<ImageData> optional = imageDataRepository.findById(id);
        if(optional.isPresent()) {
            logger.info("Is present >> ");
            Image image = new Image();
            ImageData imageDatum = optional.get();
            image.setId(imageDatum.getId());
            image.setImageURL(imageDatum.getImageURL());
            return image;
        }
        logger.info("Failed  >> unable to locate image" );
        return null;
    }

    @Override
    public void delete(Integer id) {
        Image image = null;
        logger.info("Input >> " + Integer.toString(id));
        Optional<ImageData> optional = imageDataRepository.findById(id);
        if( optional.isPresent()) {
            ImageData imageDatum = optional.get();
            imageDataRepository.delete(optional.get());
            logger.info("Successfully deleted >> " + imageDatum.toString());
            image = new Image();
            image.setId(optional.get().getId());
            image.setImageURL(optional.get().getImageURL());
        }
        else {
            logger.info("Failed  >> unable to locate image id: " +  Integer.toString(id));
        }
    }
}
