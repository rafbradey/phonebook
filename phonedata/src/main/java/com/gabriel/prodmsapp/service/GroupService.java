package com.gabriel.prodmsapp.service;

import com.gabriel.prodmsapp.model.Group;

public interface GroupService {
    Group[] getGroups() throws Exception;

    Group getGroup(Integer id) throws Exception;

    Group create(Group group) throws Exception;

    Group update(Group group) throws Exception;

    void delete(Integer id) throws Exception;
}
