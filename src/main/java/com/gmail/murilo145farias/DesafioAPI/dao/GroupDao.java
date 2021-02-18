package com.gmail.murilo145farias.DesafioAPI.dao;

import com.gmail.murilo145farias.DesafioAPI.domain.Group;

import java.util.List;
import java.util.UUID;

public interface GroupDao {

    void save(Group group);

    void update(Group group);

    void delete(UUID id);

    Group findById(UUID id);

    List<Group> findAll(String name, boolean exactMatch);

    List<Group> findAllWithoutUsers(String name, boolean exactMatch);


}
