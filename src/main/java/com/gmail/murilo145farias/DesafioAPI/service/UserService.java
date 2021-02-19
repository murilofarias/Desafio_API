package com.gmail.murilo145farias.DesafioAPI.service;

import com.gmail.murilo145farias.DesafioAPI.domain.User;

import java.util.List;

public interface UserService {

    void save(String idGroup, User user);

    void update(String idUser, String idGroup, User user);

    void delete(String idUser, String idGroup);

    User findByIdUserAndIdGroup(String idUser, String idGroup);

    List<User> findAllByGroup(String idGroup, boolean showGroupField, String name, boolean exactMatch);
}
