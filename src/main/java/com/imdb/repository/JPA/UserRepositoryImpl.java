package com.imdb.repository.JPA;

import com.imdb.model.User;
import com.imdb.repository.UserRepository;
import org.springframework.dao.DataAccessException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.Collection;
import java.util.List;

@Repository
public class UserRepositoryImpl implements UserRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Collection<User> findAll() throws DataAccessException {
        return this.entityManager.createQuery("select user from User user").getResultList();
    }

    @Override
    public Collection<User> findByLogin(String s) throws DataAccessException {
        Query query = this.entityManager.createQuery("select user from User user where user.login like :s");
        query.setParameter("s", "%" + s + "%");
        return query.getResultList();
    }

    @Override
    public User findById(Integer id) throws DataAccessException {
        Query query = this.entityManager.createQuery("select  user from User user where user.id = :id");
        query.setParameter("id", id);
        return (User)query.getSingleResult();
    }

    @Override
    public User findOneByLogin(String login) throws DataAccessException {
        Query query = this.entityManager.createQuery("select user from User user where user.login = :log");
        query.setParameter("log", login);
        return (User)query.getSingleResult();
    }

    @Override
    public void save(User user) throws DataAccessException {
        this.entityManager.persist(user);
    }

    @Override
    public void update(User user) throws DataAccessException {
        this.entityManager.merge(user);
    }
}
