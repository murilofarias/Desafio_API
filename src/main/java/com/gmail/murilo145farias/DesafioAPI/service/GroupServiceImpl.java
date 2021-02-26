package com.gmail.murilo145farias.DesafioAPI.service;

import com.gmail.murilo145farias.DesafioAPI.dao.GroupDao;
import com.gmail.murilo145farias.DesafioAPI.domain.Group;
import com.gmail.murilo145farias.DesafioAPI.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static com.gmail.murilo145farias.util.UtilAPI.validarId;

@Service
@Transactional
public class GroupServiceImpl implements GroupService {

    @Autowired
    private GroupDao dao;

    @Override
    public void save(Group group) {
        group.setCreatedAt(new Date());
        dao.save(group);


        group.getUsers()
                .parallelStream()
                .forEach(group::addUser);
        /*
        if(group.getUsers() != null) {
            //Sem esse if, o bloco no else funciona para todos os casos menos quando um só user é passado no POST
            if(group.getUsers().size() == 1){
                group.addUser(group.getUsers().get(0));
            }
            else {
                //(1)
                group.getUsers()
                        .parallelStream()
                        .forEach(group::addUser);
            }
        }*/
    }

    @Override
    public void update(String stringId, Group group) {
        UUID id = validarId(stringId);
        group.setId(id);
        group.setCreatedAt(dao.findById(id).getCreatedAt());
        group.setUsers((dao.findById(id).getUsers()));
        dao.update(group);

    }

    @Override
    public void delete(String stringId) {
        UUID id = validarId(stringId);
        dao.delete(id);
    }

    @Override
    public Group findById(String stringId) {
        UUID id = validarId(stringId);
        return dao.findById(id);
    }

    @Override
    public List<Group> findAll(String name, boolean exactMatch) {
        return dao.findAll(name, exactMatch);
    }

    @Override
    public List<Group> findAllWithoutUsers(String name, boolean exactMatch) {

        return dao.findAllWithoutUsers(name, exactMatch);
    }



}
