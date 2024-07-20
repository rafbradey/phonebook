package com.gabriel.prodmsapp.repository;

import com.gabriel.prodmsapp.entity.PhoneData;
import org.springframework.data.repository.CrudRepository;

public interface ProductDataRepository extends CrudRepository<PhoneData,Integer> {}
