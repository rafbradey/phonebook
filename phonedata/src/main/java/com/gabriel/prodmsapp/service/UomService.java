package com.gabriel.prodmsapp.service;

import com.gabriel.prodmsapp.model.Uom;

public interface UomService {
    Uom[] getUoms() throws Exception;

    Uom getUom(Integer id) throws Exception;

    Uom create(Uom uom) throws Exception;

    Uom update(Uom uom) throws Exception;

    void delete(Integer id) throws Exception;
}
