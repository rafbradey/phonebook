package com.gabriel.prodmsapp.service;

import com.gabriel.prodmsapp.model.Social;

public interface SocialService {
    Social[] getSocials() throws Exception;

    Social getSocial(Integer id) throws Exception;

    Social create(Social social) throws Exception;

    Social update(Social social) throws Exception;

    void delete(Integer id) throws Exception;
}
