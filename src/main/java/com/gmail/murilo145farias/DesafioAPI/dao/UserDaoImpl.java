package com.gmail.murilo145farias.DesafioAPI.dao;

import com.gmail.murilo145farias.DesafioAPI.domain.Group;
import com.gmail.murilo145farias.DesafioAPI.domain.User;
import com.gmail.murilo145farias.DesafioAPI.exception.NaoExisteDaoException;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.UUID;

@Repository
public class UserDaoImpl implements UserDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void save(User user) {

        entityManager.persist(user);
    }

    @Override
    public void update(User user) {

        entityManager.merge(user);
    }

    @Override
    public void delete(User user) {

        entityManager.remove(user);
    }

    @Override
    public User findByIdUserAndIdGroup(UUID idUser, UUID idGroup) {
        String query = "select v from TheUser v where v.id = ?1 and v.group.id = ?2";
        try {
            return entityManager
                    .createQuery(query, User.class)
                    .setParameter(1, idUser)
                    .setParameter(2, idGroup)
                    .getSingleResult();
        } catch (NoResultException ex) {
            throw new NaoExisteDaoException("User id = "+ idUser +
                    " não encontrada para Group id = " + idGroup + ".");
        }
    }

    @Override
    public List<User> findAllByGroup(UUID idGroup, boolean showGroupField, String name, boolean exactMatch) {
        String query = showGroupField
                ? "select v"
                : "select new TheUser(v.id, v.name, v.phone, v.createdAt)";

        query += " from TheUser v where v.group.id = ?1";
        return SearchUsers(query, idGroup, name, exactMatch);

    }

    private List<User> SearchUsers(String query, UUID idGroup,  String name, boolean exactMatch)
    {
        if(!name.equals("")) {
            query += exactMatch ? " and v.name= ?2" : " and v.name like ?2";
            name = exactMatch ? name : "%"+ name +"%";
            return entityManager
                    .createQuery(query, User.class)
                    .setParameter(1, idGroup)
                    .setParameter(2, name)
                    .getResultList();
        }

        return entityManager
                .createQuery(query, User.class)
                .setParameter(1, idGroup)
                .getResultList();
    }

}