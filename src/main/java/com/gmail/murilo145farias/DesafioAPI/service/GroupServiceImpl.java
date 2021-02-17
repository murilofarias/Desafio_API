package com.gmail.murilo145farias.DesafioAPI.service;

import com.gmail.murilo145farias.DesafioAPI.dao.GroupDao;
import com.gmail.murilo145farias.DesafioAPI.domain.Group;
import com.gmail.murilo145farias.DesafioAPI.exception.IdNaoValidoServiceException;
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
    public void update(String stringId, Group group) {
        UUID id = validarId(stringId);
        group.setId(id);
        group.setCreatedAt(dao.findById(id).getCreatedAt());
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
    public List<Group> findAll() {

        return dao.findAll();
    }

    @Override
    public Group updateDataInicio(String stringId, Date dataInicio) {
        UUID id = validarId(stringId);
        Group group = dao.findById(id);
        group.setCreatedAt(dataInicio);
        return group;
    }

    public UUID validarId(String stringId) {
        UUID id;
        try {
            id = UUID.fromString(stringId);
        } catch(IllegalArgumentException ex)
        {
            throw new IdNaoValidoServiceException("O id informado não está no formato UUID");
        }
        return id;
    }
}
