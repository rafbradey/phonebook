package com.gabriel.prodmsapp.serviceimpl;

import com.gabriel.prodmsapp.entity.GroupData;
import com.gabriel.prodmsapp.model.Group;
import com.gabriel.prodmsapp.repository.GroupDataRepository;
import com.gabriel.prodmsapp.service.GroupService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Service
public class GroupServiceImpl implements GroupService {
    Logger logger = LoggerFactory.getLogger(GroupServiceImpl.class);

    @Autowired
    GroupDataRepository groupDataRepository;

    @Override
    public Group[] getGroups() {
        List<GroupData> groupsData = new ArrayList<>();
        List<Group> groups = new ArrayList<>();
        groupDataRepository.findAll().forEach(groupsData::add);
        Iterator<GroupData> it = groupsData.iterator();

        while(it.hasNext()) {
            Group group = new Group();
            GroupData groupData = it.next();
            group.setId(groupData.getId());
            group.setName(groupData.getName());
            groups.add(group);
        }

        Group[] array = new Group[groups.size()];
        for  (int i = 0; i< groups.size(); i++){
            array[i] = groups.get(i);
        }
        return array;
    }

    @Override
    public Group create(Group group) {
        logger.info("add: Input"+ group.toString());
        GroupData groupData = new GroupData();
        groupData.setName(group.getName());

        groupData = groupDataRepository.save(groupData);
        logger.info("add: Input"+ groupData.toString());

        Group newGroup = new Group();
        newGroup.setId(groupData.getId());
        newGroup.setName(groupData.getName());
        return newGroup;
    }

    @Override
    public Group update(Group group) {
        GroupData groupData = new GroupData();
        groupData.setId(group.getId());
        groupData.setName(group.getName());

        groupData = groupDataRepository.save(groupData);

        Group newGroup = new Group();
        newGroup.setId(groupData.getId());
        newGroup.setName(groupData.getName());
        return newGroup;
    }

    @Override
    public Group getGroup(Integer id) {
        logger.info("Input id >> "+  Integer.toString(id) );
        Optional<GroupData> optional = groupDataRepository.findById(id);
        if(optional.isPresent()) {
            logger.info("Is present >> ");
            Group group = new Group();
            GroupData groupDatum = optional.get();
            group.setId(groupDatum.getId());
            group.setName(groupDatum.getName());
             return group;
        }
        logger.info("Failed  >> unable to locate group" );
        return null;
    }

    @Override
    public void delete(Integer id) {
        Group group = null;
        logger.info("Input >> " + Integer.toString(id));
         Optional<GroupData> optional = groupDataRepository.findById(id);
         if( optional.isPresent()) {
             GroupData groupDatum = optional.get();
             groupDataRepository.delete(optional.get());
             logger.info("Successfully deleted >> " + groupDatum.toString());
             group = new Group();
             group.setId(optional.get().getId());
             group.setName(optional.get().getName());
         }
         else {
             logger.info("Failed  >> unable to locate group id: " +  Integer.toString(id));
         }
    }
}
