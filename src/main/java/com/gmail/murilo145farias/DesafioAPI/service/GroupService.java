package com.gmail.murilo145farias.DesafioAPI.service;

import com.gmail.murilo145farias.DesafioAPI.domain.Group;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public interface GroupService {

    void save(Group curso);

    void update(String stringId, Group curso);

    void delete(String stringId);

    Group findById(String stringId);

    List<Group> findAll();

    Group updateDataInicio(String stringId, Date createdAt);
}
