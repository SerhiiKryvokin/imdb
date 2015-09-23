package com.imdb.service;

import com.imdb.model.Movie;
import com.imdb.model.Rating;
import com.imdb.model.composite.RatingId;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public interface RatingService {
    Collection<Rating> findAll() throws DataAccessException;
    Rating findById(RatingId id) throws DataAccessException;
    void save(Rating rating) throws DataAccessException;
    void update(Rating rating) throws DataAccessException;

    void rateMovie(UserDetails userDetails, Movie movie, Integer value) throws DataAccessException;
}
