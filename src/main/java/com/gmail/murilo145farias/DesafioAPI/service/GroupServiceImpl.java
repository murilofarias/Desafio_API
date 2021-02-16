package com.gmail.murilo145farias.DesafioAPI.service;

import com.gmail.murilo145farias.DesafioAPI.dao.GroupDao;
import com.gmail.murilo145farias.DesafioAPI.domain.Group;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class GroupServiceImpl implements GroupService {

    @Autowired
    private GroupDao dao;

    @Override
    public void save(Group group) {
        group.setCreatedAt(new Date());
        dao.save(group);
    }

    @Override
    public void update(UUID id, Group group) {
        group.setId(id);
        dao.findById(id);
        dao.update(group);
    }

    @Override
    public void delete(UUID id) {

        dao.delete(id);
    }

    @Override
    public Group findById(UUID id) {

        return dao.findById(id);
    }

    @Override
    public List<Group> findAll() {

        return dao.findAll();
    }

    @Override
    public Group updateDataInicio(UUID id, Date dataInicio) {

        Group group = dao.findById(id);
        group.setCreatedAt(dataInicio);
        return group;
    }
}
