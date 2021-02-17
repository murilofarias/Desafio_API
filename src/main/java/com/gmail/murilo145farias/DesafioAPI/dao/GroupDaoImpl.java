package com.gmail.murilo145farias.DesafioAPI.dao;

import com.gmail.murilo145farias.DesafioAPI.domain.Group;
import com.gmail.murilo145farias.DesafioAPI.exception.NaoExisteDaoException;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.UUID;

@Repository
public class GroupDaoImpl implements GroupDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void save(Group group) {

        entityManager.persist(group);
    }

    @Override
    public void update(Group group) {

        entityManager.merge(group);
    }

    @Override
    public void delete(UUID id) {
        try {
            entityManager.remove(entityManager.getReference(Group.class, id));
        } catch(EntityNotFoundException ex) {
            throw new NaoExisteDaoException("Group não encontrado para id = " + id + ".");
        }

    }

    @Override
    public Group findById(UUID id) {
        Group group = entityManager.find(Group.class, id);
        if (group == null) {
            throw new NaoExisteDaoException("Group não encontrado para id = " + id + ".");
        }
        return group;
    }

    @Override
    public List<Group> findAll() {
        return entityManager
                .createQuery("select c from Group c", Group.class)
                .getResultList();
    }
}
