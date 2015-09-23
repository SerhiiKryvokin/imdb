package com.imdb.repository;

import com.imdb.model.Rating;
import com.imdb.model.composite.RatingId;
import org.springframework.dao.DataAccessException;

import java.util.Collection;

public interface RatingRepository {
    Collection<Rating> findAll() throws DataAccessException;
    Rating findById(RatingId id) throws DataAccessException;
    void save(Rating rating) throws DataAccessException;
    void update(Rating rating) throws DataAccessException;
}
