package com.imdb.service;

import com.imdb.model.Movie;
import com.imdb.model.Rating;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public interface MovieService {
    Collection<Movie> findAll() throws DataAccessException;
    Collection<Movie> findAll(String s) throws DataAccessException;
    Collection<Movie> findAll(UserDetails userDetails) throws DataAccessException;
    Collection<Movie> findAll(UserDetails userDetails, String s) throws DataAccessException;
    Movie findById(Integer id) throws DataAccessException;
    void save(Movie movie) throws DataAccessException;
    void update(Movie movie) throws DataAccessException;
    void remove(Movie movie) throws DataAccessException;
//    void refreshAverageRating(Movie movie) throws DataAccessException;
//    void refreshAverageRating(Collection<Movie> movies) throws DataAccessException;
    void refreshAverageRating() throws DataAccessException;
    void commitRating(Rating rating) throws DataAccessException;
}
