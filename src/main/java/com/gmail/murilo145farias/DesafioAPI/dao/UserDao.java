package com.gmail.murilo145farias.DesafioAPI.dao;

import com.gmail.murilo145farias.DesafioAPI.domain.User;

import java.util.List;
import java.util.UUID;

public interface UserDao {

    void save(User user);

    void update(User user);

    void delete(User user);

    User findByIdUserAndIdGroup(UUID idUser, UUID idGroup);

    List<User> findAllByGroup(UUID idGroup, String fields);

}
