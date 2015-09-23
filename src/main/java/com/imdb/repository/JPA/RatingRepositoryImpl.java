package com.imdb.repository.JPA;

import com.imdb.model.Rating;
import com.imdb.model.composite.RatingId;
import com.imdb.repository.RatingRepository;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.Collection;

@Repository
public class RatingRepositoryImpl implements RatingRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Collection<Rating> findAll() throws DataAccessException {
        return this.entityManager.createQuery("select rating from Rating rating").getResultList();
    }

    @Override
    public Rating findById(RatingId id) throws DataAccessException {
        Query query = this.entityManager.createQuery("select rating from Rating rating where rating.primaryKey.user = :user and rating.primaryKey.movie = :movie");
        query.setParameter("user", id.getUser());
        query.setParameter("movie", id.getMovie());
        return (Rating)query.getSingleResult();
    }

    @Override
    public void save(Rating rating) throws DataAccessException {
        this.entityManager.persist(rating);
    }

    @Override
    public void update(Rating rating) throws DataAccessException {
        this.entityManager.merge(rating);
    }
}
