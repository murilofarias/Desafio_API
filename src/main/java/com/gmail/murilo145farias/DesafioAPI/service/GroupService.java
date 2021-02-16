package com.gmail.murilo145farias.DesafioAPI.service;

import com.gmail.murilo145farias.DesafioAPI.domain.Group;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public interface GroupService {

    void save(Group curso);

    void update(UUID id, Group curso);

    void delete(UUID id);

    Group findById(UUID id);

    List<Group> findAll();

    Group updateDataInicio(UUID id, Date createdAt);
}
