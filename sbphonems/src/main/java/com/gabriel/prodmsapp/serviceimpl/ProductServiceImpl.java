package com.gabriel.prodmsapp.serviceimpl;

import com.gabriel.prodmsapp.entity.ProductData;
import com.gabriel.prodmsapp.model.Product;
import com.gabriel.prodmsapp.repository.ProductDataRepository;
import com.gabriel.prodmsapp.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ProductServiceImpl implements ProductService {
    Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

    @Autowired
    ProductDataRepository productDataRepository;

    @Override
    public Product[] getProducts() {
        List<ProductData> productsData = new ArrayList<>();
        List<Product> products = new ArrayList<>();
        productDataRepository.findAll().forEach(productsData::add);
        Iterator<ProductData> it = productsData.iterator();

        while(it.hasNext()) {
            Product product = new Product();
            ProductData productData = it.next();
            product.setId(productData.getId());
            product.setName(productData.getName());
            product.setDescription(productData.getDescription());
            product.setUomId(productData.getUomId());
            product.setUomName(productData.getUomName());
            products.add(product);
        }

        Product[] array = new Product[products.size()];
        for  (int i=0; i<products.size(); i++){
            array[i] = products.get(i);
        }
//        Product[] array = (Product[])products.toArray();
        return array;
    }

    @Override
    public Product create(Product product) {
        logger.info("add: Input"+ product.toString());
        ProductData productData = new ProductData();
        productData.setName(product.getName());
        productData.setDescription(product.getDescription());
        productData.setUomId(product.getUomId());
        productData.setUomName(product.getUomName());

        productData = productDataRepository.save(productData);
        logger.info("add: Input"+ productData.toString());

        Product newProduct = new Product();
        newProduct.setId(productData.getId());
        newProduct.setName(productData.getName());
        newProduct.setDescription(productData.getDescription());
        newProduct.setUomId(productData.getUomId());
        newProduct.setUomName(productData.getUomName());
        return newProduct;
    }

    @Override
    public Product update(Product product) {
        ProductData productData = new ProductData();
        productData.setId(product.getId());
        productData.setName(product.getName());
        productData.setDescription(product.getDescription());
        productData.setUomId(product.getUomId());
        productData.setUomName(product.getUomName());

        productData = productDataRepository.save(productData);

        Product newProduct = new Product();
        newProduct.setId(productData.getId());
        newProduct.setName(productData.getName());
        newProduct.setDescription(productData.getDescription());
        newProduct.setUomId(productData.getUomId());
        newProduct.setUomName(productData.getUomName());
        return newProduct;
    }

    @Override
    public Product getProduct(Integer id) {
        logger.info("Input id >> "+  Integer.toString(id) );
        Optional<ProductData> optional = productDataRepository.findById(id);
        if(optional.isPresent()) {
            logger.info("Is present >> ");
            Product product = new Product();
            ProductData productDatum = optional.get();
            product.setId(productDatum.getId());
            product.setName(productDatum.getName());
            product.setDescription(productDatum.getDescription());
            product.setUomId(productDatum.getUomId());
            product.setUomName(productDatum.getUomName());
            return product;
        }
        logger.info("Failed  >> unable to locate product" );
        return null;
    }

    @Override
    public void delete(Integer id) {
        Product product = null;
        logger.info("Input >> " + Integer.toString(id));
         Optional<ProductData> optional = productDataRepository.findById(id);
         if( optional.isPresent()) {
             ProductData productDatum = optional.get();
             productDataRepository.delete(optional.get());
             logger.info("Successfully deleted >> " + productDatum.toString());
             product = new Product();
             product.setId(optional.get().getId());
             product.setName(optional.get().getName());
             product.setDescription(optional.get().getDescription());
             product.setUomId(optional.get().getUomId());
             product.setUomName(optional.get().getUomName());
         }
         else {
             logger.info("Failed  >> unable to locate product id: " +  Integer.toString(id));
         }
    }
}
