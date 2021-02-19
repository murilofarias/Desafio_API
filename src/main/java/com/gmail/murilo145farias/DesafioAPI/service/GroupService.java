package com.gmail.murilo145farias.DesafioAPI.service;

import com.gmail.murilo145farias.DesafioAPI.domain.Group;
import com.gmail.murilo145farias.DesafioAPI.domain.User;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public interface GroupService {

    void save(Group curso);

    void update(String stringId, Group curso);

    void delete(String stringId);

    Group findById(String stringId);

    List<Group> findAll(String name, boolean exactMatch);

    List<Group> findAllWithoutUsers(String name, boolean exactMatch);

}
