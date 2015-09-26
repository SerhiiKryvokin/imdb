package com.imdb.repository;

import com.imdb.model.Movie;
import com.imdb.model.User;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public interface MovieRepository {
    Collection<Movie> findAll() throws DataAccessException;
    Collection<Movie> findAll(String s) throws DataAccessException;
    Collection<Movie> findAll(User user) throws DataAccessException;
    Collection<Movie> findAll(User user, String s) throws DataAccessException;
    Movie findById(Integer id) throws DataAccessException;
    void save(Movie movie) throws DataAccessException;
    void update(Movie movie) throws DataAccessException;
    void remove(Movie movie) throws DataAccessException;

    void refreshAverageRating(Movie movie) throws DataAccessException;
}
