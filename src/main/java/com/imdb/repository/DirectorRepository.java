package com.imdb.repository;

import com.imdb.model.Director;
import com.imdb.model.User;
import org.springframework.dao.DataAccessException;

import java.util.Collection;

public interface DirectorRepository {
    Collection<Director> findAll() throws DataAccessException;
    Collection<Director> findByName(String s) throws DataAccessException;
    Director findById(Integer id) throws DataAccessException;
    void save(Director director) throws DataAccessException;
    void update(Director director) throws DataAccessException;
}
