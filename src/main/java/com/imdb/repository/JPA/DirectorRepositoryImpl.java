package com.imdb.repository.JPA;

import com.imdb.model.Director;
import com.imdb.model.User;
import com.imdb.repository.DirectorRepository;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.Collection;

@Repository
public class DirectorRepositoryImpl implements DirectorRepository{

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Collection<Director> findAll() throws DataAccessException {
        return this.entityManager.createQuery("select director from Director director").getResultList();
    }

    @Override
    public Collection<Director> findByName(String s) throws DataAccessException {
        Query query = this.entityManager.createQuery("select director from Director director where lower(director.name) like :s");
        query.setParameter("s", "%" + s + "%");
        return query.getResultList();
    }

    @Override
    public Director findById(Integer id) throws DataAccessException {
        Query query = this.entityManager.createQuery("select director from Director director where director.id = :id");
        query.setParameter("id", id);
        return (Director)query.getSingleResult();
    }

    @Override
    public void save(Director director) throws DataAccessException {
        this.entityManager.persist(director);
    }

    @Override
    public void update(Director director) throws DataAccessException {
        this.entityManager.merge(director);
    }
}
