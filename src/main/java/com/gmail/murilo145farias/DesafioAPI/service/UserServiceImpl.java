package com.gmail.murilo145farias.DesafioAPI.service;

import com.gmail.murilo145farias.DesafioAPI.dao.UserDao;
import com.gmail.murilo145farias.DesafioAPI.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

import static com.gmail.murilo145farias.util.UtilAPI.validarId;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao dao;
    @Autowired
    private GroupService groupService;

    @Override
    public void save(String idGroup, User user) {
        user.setGroup(groupService.findById(idGroup));
        dao.save(user);
    }


    @Override
    public void update(String idUser, String idGroup, User user) {
        user.setId(validarId(idUser));
        User unupdatedUser = findByIdUserAndIdGroup(idUser, idGroup);
        user.setCreatedAt(unupdatedUser.getCreatedAt());
        user.setGroup(unupdatedUser.getGroup());
        dao.update(user);
    }

    @Override
    public void delete(String idUser, String idGroup) {

        dao.delete(findByIdUserAndIdGroup(idUser, idGroup));
    }

    @Override
    public User findByIdUserAndIdGroup(String idUser, String idGroup) {
        return dao.findByIdUserAndIdGroup(validarId(idUser), validarId(idGroup));
    }

    @Override
    public List<User> findAllByGroup(String idGroup, boolean showGroupField, String name, boolean exactMatch) {
        return dao.findAllByGroup(validarId(idGroup), showGroupField, name, exactMatch);
    }
}
